package me.chiqors.minimarket_backend.validation;

import me.chiqors.minimarket_backend.dto.CustomerDTO;
import me.chiqors.minimarket_backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CustomerValidation {
    @Autowired
    private CustomerService customerService;

    /**
     * Create customer validation
     *
     * @param customerDTO CustomerDTO object
     * @return List<String> errors
     */
    public List<String> createCustomerValidation(CustomerDTO customerDTO) {
        List<String> errors = new ArrayList<>();

        if (customerDTO.getName() == null) {
            errors.add("Name is required");
        } else if (customerDTO.getName().length() < 3) {
            errors.add("Name must be at least 3 characters");
        }

        // gender: 'M' or 'F'
        if (customerDTO.getGender() == null) {
            errors.add("Gender is required");
        } else if (!customerDTO.getGender().equals("M") && !customerDTO.getGender().equals("F")) {
            errors.add("Invalid gender");
        }

        if (customerDTO.getBirthDate() == null) {
            errors.add("Birth date is required");
        } else {
            Date currentDate = new Date(); // Current date
            Date birthDate = customerDTO.getBirthDate(); // Customer's birthdate

            if (birthDate.after(currentDate)) {
                errors.add("Invalid birth date");
            } else {
                Date minValidDate = new Date(currentDate.getYear() - 100, currentDate.getMonth(), currentDate.getDate());

                if (birthDate.before(minValidDate)) {
                    errors.add("Invalid birth date");
                }
            }
        }

        if (customerDTO.getAddress() == null) {
            errors.add("Address is required");
        } else if (customerDTO.getAddress().length() < 3) {
            errors.add("Address must be at least 3 characters");
        }

        if (customerDTO.getPhoneNumber() == null) {
            errors.add("Phone number is required");
        } else if (customerDTO.getPhoneNumber().length() < 3 || customerDTO.getPhoneNumber().length() > 15) {
            errors.add("Phone number must be between 3 and 15 characters");
        } else if (!customerDTO.getPhoneNumber().matches("^[0-9]*$")) {
            errors.add("Phone number must be numeric");
        } else if (customerService.isPhoneNumberExist(customerDTO.getPhoneNumber())) {
            errors.add("Phone number already exist");
        }

        return errors;
    }

    /**
     * Update customer validation
     *
     * @param customerDTO CustomerDTO object
     * @return List<String> errors
     */
    public List<String> updateCustomerValidation(CustomerDTO customerDTO) {
        List<String> errors = new ArrayList<>();

        // at least one field must be filled: name, gender, birthdate, address, phone number
        if (customerDTO.getName() == null && customerDTO.getGender() == null && customerDTO.getBirthDate() == null && customerDTO.getAddress() == null && customerDTO.getPhoneNumber() == null) {
            errors.add("At least one field must be filled: name, gender, birthdate, address, phone number");
        }

        if (customerDTO.getCustomerCode() == null) {
            errors.add("Customer code is required");
        } else if (!customerService.isCustomerCodeExist(customerDTO.getCustomerCode())) {
            errors.add("Customer code not found");
        }

        if (customerDTO.getName() != null && customerDTO.getName().length() < 3) {
            errors.add("Name must be at least 3 characters");
        }

        if (customerDTO.getGender() != null && !customerDTO.getGender().equals("M") && !customerDTO.getGender().equals("F")) {
            errors.add("Invalid gender");
        }

        if (customerDTO.getBirthDate() != null) {
            Date currentDate = new Date(); // Current date
            Date birthDate = customerDTO.getBirthDate(); // Customer's birthdate

            if (birthDate.after(currentDate)) {
                errors.add("Invalid birth date");
            } else {
                Date minValidDate = new Date(currentDate.getYear() - 100, currentDate.getMonth(), currentDate.getDate());

                if (birthDate.before(minValidDate)) {
                    errors.add("Invalid birth date");
                }
            }
        }

        if (customerDTO.getAddress() != null && customerDTO.getAddress().length() < 3) {
            errors.add("Address must be at least 3 characters");
        }

        if (customerDTO.getPhoneNumber() != null) {
            if (customerDTO.getPhoneNumber().length() < 3 || customerDTO.getPhoneNumber().length() > 15) {
                errors.add("Phone number must be between 3 and 15 characters");
            } else if (!customerDTO.getPhoneNumber().matches("^[0-9]*$")) {
                errors.add("Phone number must be numeric");
            } else if (customerService.isPhoneNumberExist(customerDTO.getPhoneNumber())) {
                errors.add("Phone number already exist");
            }
        }

        return errors;
    }
}
