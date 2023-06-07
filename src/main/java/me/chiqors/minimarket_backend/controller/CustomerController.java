package me.chiqors.minimarket_backend.controller;

import me.chiqors.minimarket_backend.dto.CustomerDTO;
import me.chiqors.minimarket_backend.service.CustomerService;
import me.chiqors.minimarket_backend.util.JSONResponse;
import me.chiqors.minimarket_backend.validation.CustomerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("${api.prefix}")
public class CustomerController {
    @Autowired
    private CustomerValidation customerValidation;
    @Autowired
    private CustomerService customerService;

    /**
     * Get all customers with pagination and search by name
     *
     * @param name Customer name
     * @param page Page number
     * @param size Page size
     * @return ResponseEntity<JSONResponse> with status code and JSONResponse object
     */
    @GetMapping("/customers")
    public ResponseEntity<JSONResponse> getAllCustomers(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false, defaultValue = "1") Integer page,
                                                        @RequestParam(required = false, defaultValue = "3") Integer size) {
        try {
            Page<CustomerDTO> customerPage = customerService.getAllCustomers(name, page, size);
            if (customerPage != null) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.FOUND.value(), "Customer retrieved", customerPage, null);
                return ResponseEntity.ok(jsonResponse);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Customer not found", null, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve customer", null, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
        }
    }

    /**
     * Get customer by code
     *
     * @param customerCode Customer code
     * @return ResponseEntity<JSONResponse> with status code and JSONResponse object
     */
    @GetMapping("/customers/{customerCode}")
    public ResponseEntity<JSONResponse> getCustomerByCode(@PathVariable("customerCode") String customerCode) {
        try {
            CustomerDTO customerDTO = customerService.getCustomerByCustomerCode(customerCode);
            if (customerDTO != null) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.FOUND.value(), "Customer retrieved", customerDTO, null);
                return ResponseEntity.ok(jsonResponse);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Customer not found", null, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve customer", null, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
        }
    }

    /**
     * Create new customer
     *
     * @param customerDTO CustomerDTO object
     * @return ResponseEntity<JSONResponse> with status code and JSONResponse object
     */
    @PostMapping("/customers")
    public ResponseEntity<JSONResponse> createCustomer(@RequestBody CustomerDTO customerDTO) {
        List<String> errors = customerValidation.createCustomerValidation(customerDTO);
        if (errors.isEmpty()) {
            try {
                CustomerDTO customer = customerService.createCustomer(customerDTO);
                if (customer != null) {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.CREATED.value(), "Customer created", customer, null);
                    return ResponseEntity.status(HttpStatus.CREATED).body(jsonResponse);
                } else {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Failed to create customer", null, null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create customer", null, null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Failed to create customer", null, errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }

    /**
     * Update customer
     *
     * @param customerDTO CustomerDTO object
     * @return ResponseEntity<JSONResponse> with status code and JSONResponse object
     */
    @PutMapping("/customers")
    public ResponseEntity<JSONResponse> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        List<String> errors = customerValidation.updateCustomerValidation(customerDTO);
        if (errors.isEmpty()) {
            try {
                CustomerDTO customer = customerService.updateCustomer(customerDTO);
                if (customer != null) {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Customer updated", customer, null);
                    return ResponseEntity.ok(jsonResponse);
                } else {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Failed to update customer", null, null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update customer", null, null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Failed to update customer", null, errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }
}
