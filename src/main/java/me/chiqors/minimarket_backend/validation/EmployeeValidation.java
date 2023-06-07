package me.chiqors.minimarket_backend.validation;

import me.chiqors.minimarket_backend.dto.EmployeeDTO;
import me.chiqors.minimarket_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class EmployeeValidation {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Create employee validation
     *
     * @param employeeDTO EmployeeDTO object
     * @return List<String> errors
     */
    public List<String> createEmployeeValidation(EmployeeDTO employeeDTO) {
        List<String> errors = new ArrayList<>();

        if (employeeDTO.getAccount() != null) {
            if (employeeDTO.getAccount().getUsername() == null) {
                errors.add("Username is required");
            } else if (employeeDTO.getAccount().getUsername().length() < 3 || employeeDTO.getAccount().getUsername().length() > 20) {
                errors.add("Username must be at least 3 characters and maximum 20 characters");
            } else if (employeeService.findByUsername(employeeDTO.getAccount().getUsername())) {
                errors.add("Username already exists");
            }

            if (employeeDTO.getAccount().getPassword() == null) {
                errors.add("Password is required");
            } else if (employeeDTO.getAccount().getPassword().length() < 3 || employeeDTO.getAccount().getPassword().length() > 20) {
                errors.add("Password must be at least 3 characters and maximum 20 characters");
            }

            if (employeeDTO.getAccount().getEmail() == null) {
                errors.add("Email is required");
            } else if (employeeDTO.getAccount().getEmail().length() < 3 || employeeDTO.getAccount().getEmail().length() > 50) {
                errors.add("Email must be at least 3 characters and maximum 50 characters");
            } else if (employeeService.findByEmail(employeeDTO.getAccount().getEmail())) {
                errors.add("Email already exists");
            }

            if (employeeDTO.getAccount().getRole() == null) {
                errors.add("Role is required");
            } else if (employeeDTO.getAccount().getRole() != 1 && employeeDTO.getAccount().getRole() != 2) {
                errors.add("Invalid role");
            }
        } else {
            errors.add("Account is required");
        }

        if (employeeDTO.getName() == null) {
            errors.add("Name is required");
        } else if (employeeDTO.getName().length() < 3) {
            errors.add("Name must be at least 3 characters");
        }

        // gender: 'M' or 'F'
        if (employeeDTO.getGender() == null) {
            errors.add("Gender is required");
        } else if (!employeeDTO.getGender().equals("M") && !employeeDTO.getGender().equals("F")) {
            errors.add("Invalid gender");
        }

        if (employeeDTO.getBirthDate() == null) {
            errors.add("Birth date is required");
        } else {
            Date currentDate = new Date(); // Current date
            Date birthDate = employeeDTO.getBirthDate(); // Customer's birthdate

            if (birthDate.after(currentDate)) {
                errors.add("Invalid birth date");
            } else {
                Date minValidDate = new Date(currentDate.getYear() - 100, currentDate.getMonth(), currentDate.getDate());

                if (birthDate.before(minValidDate)) {
                    errors.add("Invalid birth date");
                }
            }
        }

        if (employeeDTO.getAddress() == null) {
            errors.add("Address is required");
        } else if (employeeDTO.getAddress().length() < 3) {
            errors.add("Address must be at least 3 characters");
        }

        if (employeeDTO.getPhoneNumber() == null) {
            errors.add("Phone number is required");
        } else if (employeeDTO.getPhoneNumber().length() < 3 || employeeDTO.getPhoneNumber().length() > 15) {
            errors.add("Phone number must be between 3 and 15 characters");
        } else if (!employeeDTO.getPhoneNumber().matches("^[0-9]*$")) {
            errors.add("Phone number must be numeric");
        } else if (employeeService.isPhoneNumberExist(employeeDTO.getPhoneNumber())) {
            errors.add("Phone number already exist");
        }

        return errors;
    }

    /**
     * Update employee validation
     *
     * @param employeeDTO EmployeeDTO object
     * @return List<String> errors
     */
    public List<String> updateEmployeeValidation(EmployeeDTO employeeDTO) {
        List<String> errors = new ArrayList<>();

        // at least one field must be filled: password, email, role, name, gender, birthDate, address, phoneNumber
        if (employeeDTO.getAccount() == null && employeeDTO.getName() == null && employeeDTO.getGender() == null && employeeDTO.getBirthDate() == null && employeeDTO.getAddress() == null && employeeDTO.getPhoneNumber() == null) {
            errors.add("At least one field must be filled: password, email, role, name, gender, birthDate, address, phoneNumber");
        }

        if (employeeDTO.getEmployeeCode() == null) {
            errors.add("Employee code is required");
        } else if (!employeeService.isEmployeeCodeExist(employeeDTO.getEmployeeCode())) {
            errors.add("Employee code doesn't exist");
        }

        if (employeeDTO.getName() == null) {
            errors.add("Name is required");
        } else if (employeeDTO.getName().length() < 3) {
            errors.add("Name must be at least 3 characters");
        }

        // gender: 'M' or 'F'
        if (employeeDTO.getGender() == null) {
            errors.add("Gender is required");
        } else if (!employeeDTO.getGender().equals("M") && !employeeDTO.getGender().equals("F")) {
            errors.add("Invalid gender");
        }

        if (employeeDTO.getBirthDate() == null) {
            errors.add("Birth date is required");
        } else {
            Date currentDate = new Date(); // Current date
            Date birthDate = employeeDTO.getBirthDate(); // Customer's birthdate

            if (birthDate.after(currentDate)) {
                errors.add("Invalid birth date");
            } else {
                Date minValidDate = new Date(currentDate.getYear() - 100, currentDate.getMonth(), currentDate.getDate());

                if (birthDate.before(minValidDate)) {
                    errors.add("Invalid birth date");
                }
            }
        }

        if (employeeDTO.getAddress() == null) {
            errors.add("Address is required");
        } else if (employeeDTO.getAddress().length() < 3) {
            errors.add("Address must be at least 3 characters");
        }

        if (employeeDTO.getPhoneNumber() == null) {
            errors.add("Phone number is required");
        } else if (employeeDTO.getPhoneNumber().length() < 3 || employeeDTO.getPhoneNumber().length() > 15) {
            errors.add("Phone number must be between 3 and 15 characters");
        } else if (!employeeDTO.getPhoneNumber().matches("^[0-9]*$")) {
            errors.add("Phone number must be numeric");
        } else if (employeeService.isPhoneNumberExist(employeeDTO.getPhoneNumber())) {
            errors.add("Phone number already exist");
        }

        return errors;
    }
}
