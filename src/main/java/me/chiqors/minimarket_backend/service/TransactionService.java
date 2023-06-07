package me.chiqors.minimarket_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chiqors.minimarket_backend.dto.*;
import me.chiqors.minimarket_backend.model.*;
import me.chiqors.minimarket_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionDetailRepository transactionDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Convert Transaction to TransactionDTO
     *
     * @param transaction Transaction object to be converted
     * @return TransactionDTO object
     */
    public TransactionDTO convertToTransactionDTO(Transaction transaction) {
        EmployeeDTO employeeDTO = convertToEmployeeDTO(transaction.getEmployee());
        CustomerDTO customerDTO = convertToCustomerDTO(transaction.getCustomer());
        List<TransactionDetailDTO> transactionDetailDTOList = convertToTransactionDetailDTO(transaction.getTransactionDetails());

        return new TransactionDTO(
                transaction.getTransactionCode(),
                transaction.getCreatedAt().toString(),
                transaction.getUpdatedAt().toString(),
                transaction.getStatus(),
                transaction.getTotalProducts(),
                transaction.getTotalPrice(),
                employeeDTO,
                customerDTO,
                transactionDetailDTOList
        );
    }

    /**
     * Convert Employee to EmployeeDTO
     *
     * @param employee Employee object to be converted
     * @return EmployeeDTO object
     */
    public EmployeeDTO convertToEmployeeDTO(Employee employee) {
        AccountDTO accountDTO = new AccountDTO(
                employee.getAccount().getUsername(),
                employee.getAccount().getEmail(),
                employee.getAccount().getStatus(),
                employee.getAccount().getRole(),
                employee.getAccount().getCreatedAt(),
                employee.getAccount().getUpdatedAt()
        );

        return new EmployeeDTO(
                employee.getEmployeeCode(),
                accountDTO,
                employee.getName(),
                employee.getGender(),
                employee.getBirthDate(),
                employee.getAddress(),
                employee.getPhoneNumber(),
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }

    /**
     * Convert Customer to CustomerDTO
     *
     * @param customer Customer object to be converted
     * @return CustomerDTO object
     */
    public CustomerDTO convertToCustomerDTO(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerCode(),
                customer.getName(),
                customer.getGender(),
                customer.getBirthDate(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    /**
     * Convert Product to ProductDTO
     *
     * @param product Product object to be converted
     * @return ProductDTO object
     */
    public ProductDTO convertToProductDTO(Product product) {
        ProductCategoryDTO productCategoryDTO = convertToProductCategoryDTO(product.getProductCategory());

        return new ProductDTO(
                product.getSkuCode(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getSlug(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                productCategoryDTO
        );
    }

    /**
     * Convert ProductCategory to ProductCategoryDTO
     *
     * @param productCategory ProductCategory object to be converted
     * @return ProductCategoryDTO object
     */
    public ProductCategoryDTO convertToProductCategoryDTO(ProductCategory productCategory) {
        return new ProductCategoryDTO(
                productCategory.getSlug(),
                productCategory.getName(),
                productCategory.getSkuCreated(),
                productCategory.getCreatedAt(),
                productCategory.getUpdatedAt()
        );
    }

    /**
     * Convert List of Detail Transaction to List of TransactionDetailDTO
     *
     * @param transactionDetail List of Detail Transaction object to be converted
     * @return List of TransactionDetailDTO object
     */
    public List<TransactionDetailDTO> convertToTransactionDetailDTO(List<TransactionDetail> transactionDetail) {
        List<TransactionDetailDTO> transactionDetailDTOList = new ArrayList<>();

        for (TransactionDetail td : transactionDetail) {
            ProductDTO productDTO = convertToProductDTO(td.getProduct());

            TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO(
                    td.getTransaction().getTransactionCode(),
                    td.getProduct().getSkuCode(),
                    td.getQuantity(),
                    productDTO
            );

            transactionDetailDTOList.add(transactionDetailDTO);
        }

        return transactionDetailDTOList;
    }

    /**
     * Get all transaction
     *
     * @param page Page number of transaction
     * @param size Size of transaction
     * @return Page of TransactionDTO
     */
    public Page<TransactionDTO> getAllTransaction(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);

        return transactionPage.map(this::convertToTransactionDTO);
    }

    /**
     * Get transaction by transaction code
     *
     * @param transactionCode Transaction code
     * @return TransactionDTO
     */
    public TransactionDTO getTransactionByTransactionCode(String transactionCode) {
        Transaction transaction = transactionRepository.findByTransactionCode(transactionCode);
        if (transaction != null) {
            return convertToTransactionDTO(transaction);
        } else {
            return null;
        }
    }

    /**
     * Get transaction by employee code
     *
     * @param customerCode Customer code of transaction
     * @return Boolean value of transaction exists or not
     */
    public boolean isCustomerExists(String customerCode) {
        Customer customer = customerRepository.findByCustomerCode(customerCode);
        return customer != null;
    }

    /**
     * Get transaction by employee code
     *
     * @param employeeCode Employee code of transaction
     * @return Boolean value of transaction exists or not
     */
    public boolean isEmployeeExists(String employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
        return employee != null;
    }

    /**
     * Get transaction by product sku
     *
     * @param productSku Product sku of transaction
     * @return Boolean value of transaction exists or not
     */
    public boolean isProductExists(String productSku) {
        Product product = productRepository.findBySkuCode(productSku);
        return product != null;
    }

    /**
     * Get transaction by product sku
     *
     * @param productSku Product sku of transaction
     * @param quantity Quantity of product
     * @return Boolean value of product quantity enough or not
     */
    public boolean isProductQuantityEnough(String productSku, Integer quantity) {
        Product product = productRepository.findBySkuCode(productSku);
        return product.getStock() >= quantity;
    }

    /**
     * Create new transaction
     *
     * @param transactionDTO TransactionDTO object
     * @return TransactionDTO object
     */
    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        // generate transaction code
        String transactionCode = "TRX-" + System.currentTimeMillis();

        Transaction transaction = new Transaction();
        transaction.setTransactionCode(transactionCode);
        transaction.setStatus(1);

        Employee employee = employeeRepository.findByEmployeeCode(transactionDTO.getEmployee().getEmployeeCode());
        transaction.setEmployee(employee);

        Customer customer = customerRepository.findByCustomerCode(transactionDTO.getCustomer().getCustomerCode());
        transaction.setCustomer(customer);

        List<TransactionDetail> transactionDetailList = new ArrayList<>();
        int totalProducts = 0;
        double totalPrice = 0;
        for (TransactionDetailDTO td : transactionDTO.getTransactionDetails()) {
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setQuantity(td.getQuantity());
            totalProducts += td.getQuantity();

            Product product = productRepository.findBySkuCode(td.getProductSku());
            transactionDetail.setProduct(product);

            transactionDetail.setTransaction(transaction);

            // update price
            totalPrice += product.getPrice() * td.getQuantity();

            // update stock
            product.setStock(product.getStock() - td.getQuantity());

            // create snapshot of product to JSON Format
            ObjectMapper objectMapper = new ObjectMapper();
            String productSnapshot = null;
            try {
                productSnapshot = objectMapper.writeValueAsString(product);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            transactionDetail.setSnapshot(productSnapshot);

            transactionDetailList.add(transactionDetail);

            // Save the transaction detail to the database
            transactionDetailRepository.save(transactionDetail);

            // Save the updated product stock to the database
            productRepository.save(product);
        }

        transaction.setTotalPrice(totalPrice);
        transaction.setTotalProducts(totalProducts);
        transaction.setTransactionDetails(transactionDetailList);

        Transaction newTransaction = transactionRepository.save(transaction);

        return convertToTransactionDTO(newTransaction);
    }
}
