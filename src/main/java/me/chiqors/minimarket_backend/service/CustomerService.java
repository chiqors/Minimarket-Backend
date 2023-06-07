package me.chiqors.minimarket_backend.service;

import me.chiqors.minimarket_backend.dto.CustomerDTO;
import me.chiqors.minimarket_backend.model.Customer;
import me.chiqors.minimarket_backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Convert CustomerDTO to Customer
     *
     * @param customer Customer object
     * @return Customer
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
     * Get all customers with pagination and search by name
     *
     * @param name Customer name
     * @param page Page number
     * @param size Page size
     * @return Page<CustomerDTO>
     */
    public Page<CustomerDTO> getAllCustomers(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Customer> customerPage;

        if (name != null) {
            customerPage = customerRepository.findAllByNameContaining(name, pageable);
        } else {
            customerPage = customerRepository.findAll(pageable);
        }

        return customerPage.map(this::convertToCustomerDTO);
    }

    /**
     * Get customer by customer code
     *
     * @param customerCode Customer code
     * @return CustomerDTO
     */
    public CustomerDTO getCustomerByCustomerCode(String customerCode) {
        Customer customer = customerRepository.findByCustomerCode(customerCode);
        if (customer != null) {
            return convertToCustomerDTO(customer);
        } else {
            return null;
        }
    }

    /**
     * Check if customer code exist
     *
     * @param phoneNumber Customer phone number
     * @return boolean
     */
    public boolean isPhoneNumberExist(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);

        return customer != null;
    }

    /**
     * Check if customer code exist
     *
     * @param customerCode Customer code
     * @return boolean
     */
    public boolean isCustomerCodeExist(String customerCode) {
        Customer customer = customerRepository.findByCustomerCode(customerCode);

        return customer != null;
    }

    /**
     * Create new customer
     *
     * @param customerDTO CustomerDTO object
     * @return CustomerDTO
     */
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // generate code (e.g. C<timestamp>)
        long timestamp = System.currentTimeMillis();
        String customerCode = "C" + timestamp;

        Customer customer = new Customer(
                customerCode,
                customerDTO.getName(),
                customerDTO.getGender(),
                customerDTO.getBirthDate(),
                customerDTO.getAddress(),
                customerDTO.getPhoneNumber()
        );

        customerRepository.save(customer);

        return convertToCustomerDTO(customer);
    }

    /**
     * Update customer
     *
     * @param customerDTO CustomerDTO object
     * @return CustomerDTO
     */
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findByCustomerCode(customerDTO.getCustomerCode());
        if (existingCustomer != null) {
            if (customerDTO.getName() != null) {
                existingCustomer.setName(customerDTO.getName());
            }
            if (customerDTO.getGender() != null) {
                existingCustomer.setGender(customerDTO.getGender());
            }
            if (customerDTO.getBirthDate() != null) {
                existingCustomer.setBirthDate(customerDTO.getBirthDate());
            }
            if (customerDTO.getAddress() != null) {
                existingCustomer.setAddress(customerDTO.getAddress());
            }
            if (customerDTO.getPhoneNumber() != null) {
                existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
            }

            customerRepository.save(existingCustomer);

            return convertToCustomerDTO(existingCustomer);
        } else {
            return null;
        }
    }
}
