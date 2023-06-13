package me.chiqors.minimarket_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chiqors.minimarket_backend.dto.*;
import me.chiqors.minimarket_backend.dto.custom.CustomerPurchasedDTO;
import me.chiqors.minimarket_backend.dto.custom.MostPurchasedProductDTO;
import me.chiqors.minimarket_backend.dto.custom.TransactionBetweenDateDTO;
import me.chiqors.minimarket_backend.model.*;
import me.chiqors.minimarket_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
     * Convert List of Transaction to List of TransactionBetweenDateDTO
     *
     * @param transaction List of Transaction object to be converted
     * @return List of TransactionBetweenDateDTO object
     */
    public TransactionBetweenDateDTO convertToTransactionBetweenDateDTO(Transaction transaction) {
        return new TransactionBetweenDateDTO(
                transaction.getTransactionCode(),
                transaction.getCreatedAt().toString(),
                transaction.getUpdatedAt().toString(),
                transaction.getStatus(),
                transaction.getTotalProducts(),
                transaction.getTotalPrice(),
                transaction.getEmployee().getName(),
                transaction.getCustomer().getName()
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
        Page<Transaction> transactionPage = transactionRepository.findAllByOrderByCreatedAtDesc(pageable);

        return transactionPage.map(this::convertToTransactionDTO);
    }

    /**
     * Get sum of total price of transaction in between date
     *
     * @param start_date Start date of transaction
     * @param end_date End date of transaction
     * @return Double of sum of total price
     */
    public Double getTransactionSum(Date start_date, Date end_date) {
        if (start_date == null) {
            // day-7 from now
            start_date = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
        }
        if (end_date == null) {
            // now
            end_date = new Date(System.currentTimeMillis());
        }
        // add 1 day to endDate to get transaction in endDate
        Calendar c = Calendar.getInstance();
        c.setTime(end_date);
        c.add(Calendar.DATE, 1);
        end_date = c.getTime();
        Double sum = transactionRepository.sumTotalPrice(start_date, end_date);
        if (sum == null) {
            sum = 0.0;
        }
        return sum;
    }

    /**
     * Get a list of transaction and the total of transaction in between date
     *
     * @param startDate Start date of transaction
     * @param endDate   End date of transaction
     */
    public Page<TransactionBetweenDateDTO> getTransactionByDate(Date startDate, Date endDate, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        if (startDate == null) {
            // day-7 from now
            startDate = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
        }
        if (endDate == null) {
            // now
            endDate = new Date(System.currentTimeMillis());
        }
        // add 1 day to endDate to get transaction in endDate
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();
        Page<Transaction> transactionPage = transactionRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(startDate, endDate, pageable);

        return transactionPage.map(this::convertToTransactionBetweenDateDTO);
    }

    /**
     * Get the three most bought product
     *
     * @return List of MostPurchaseProductDTO
     */
    public List<MostPurchasedProductDTO> getMostPurchasedProduct() {
        List<Object[]> mostPurchasedProductList = transactionDetailRepository.findMostPurchasedProduct();
        List<MostPurchasedProductDTO> mostPurchasedProductDTOList = new ArrayList<>();

        for (Object[] mostPurchasedProduct : mostPurchasedProductList) {

            MostPurchasedProductDTO mostPurchasedProductDTO = new MostPurchasedProductDTO(
                    mostPurchasedProduct[0].toString(),
                    mostPurchasedProduct[1].toString(),
                    mostPurchasedProduct[2].toString(),
                    Integer.parseInt(mostPurchasedProduct[3].toString()),
                    Double.parseDouble(mostPurchasedProduct[4].toString()),
                    Integer.parseInt(mostPurchasedProduct[5].toString())
            );

            mostPurchasedProductDTOList.add(mostPurchasedProductDTO);
        }

        return mostPurchasedProductDTOList;
    }

    /**
     * Get the list of customer who purchased items from transactions in between date
     *
     * @param startDate Start date of transaction
     * @param endDate End date of transaction
     * @param page Page number of transaction
     * @param size Size of transaction
     * @return Page of CustomerPurchasedDTO
     */
    public Page<CustomerPurchasedDTO> getCustomerPurchasedBetweenDate(Date startDate, Date endDate, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        if (startDate == null) {
            // day-7 from now
            startDate = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
        }
        if (endDate == null) {
            // now
            endDate = new Date(System.currentTimeMillis());
        }
        // add 1 day to endDate to get transaction in endDate
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();
        Page<Object[]> customerTransactionSummaryPage = customerRepository.findAllCustomerTransactionSummary(startDate, endDate, pageable);
        List<CustomerPurchasedDTO> customerPurchasedDTOList = new ArrayList<>();

        for (Object[] customerTransactionSummary : customerTransactionSummaryPage) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date birthDate = formatter2.parse(customerTransactionSummary[5].toString());
                Date createdAt = formatter.parse(customerTransactionSummary[6].toString());
                Date updatedAt = formatter.parse(customerTransactionSummary[7].toString());

                CustomerPurchasedDTO customerPurchasedDTO = new CustomerPurchasedDTO(
                        customerTransactionSummary[0].toString(),
                        customerTransactionSummary[1].toString(),
                        customerTransactionSummary[2].toString(),
                        customerTransactionSummary[3].toString(),
                        customerTransactionSummary[4].toString(),
                        birthDate,
                        createdAt,
                        updatedAt,
                        Double.parseDouble(customerTransactionSummary[8].toString()),
                        Integer.parseInt(customerTransactionSummary[9].toString())
                );

                customerPurchasedDTOList.add(customerPurchasedDTO);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return new PageImpl<>(customerPurchasedDTOList, pageable, customerTransactionSummaryPage.getTotalElements());
    }

    /**
     * Get the list of product that often purchased by customer
     * Desc: User input product skuCode, then the system will return the list of product that often purchased with the input product by customer
     *
     * @param skuCode Product skuCode
     * @return List of MostPurchasedProductDTO
     */
    public List<MostPurchasedProductDTO> getProductOftenPurchased(String skuCode) {
        List<Object[]> productOftenPurchasedList = transactionDetailRepository.findProductOftenPurchased(skuCode);
        List<MostPurchasedProductDTO> mostPurchasedProductDTOList = new ArrayList<>();

        for (Object[] productOftenPurchased : productOftenPurchasedList) {
            MostPurchasedProductDTO mostPurchasedProductDTO = new MostPurchasedProductDTO(
                    productOftenPurchased[0].toString(),
                    productOftenPurchased[1].toString(),
                    productOftenPurchased[2].toString(),
                    Integer.parseInt(productOftenPurchased[3].toString()),
                    Double.parseDouble(productOftenPurchased[4].toString()),
                    Integer.parseInt(productOftenPurchased[5].toString())
            );

            mostPurchasedProductDTOList.add(mostPurchasedProductDTO);
        }

        return mostPurchasedProductDTOList;
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
        }

        transaction.setTotalPrice(totalPrice);
        transaction.setTotalProducts(totalProducts);
        transaction.setTransactionDetails(transactionDetailList);

        // Save the transaction details to the database
        transactionDetailRepository.saveAll(transactionDetailList);

        // Save the updated product stock to the database
        for (TransactionDetail transactionDetail : transactionDetailList) {
            productRepository.save(transactionDetail.getProduct());
        }

        Transaction newTransaction = transactionRepository.save(transaction);

        return convertToTransactionDTO(newTransaction);
    }
}
