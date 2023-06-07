package me.chiqors.minimarket_backend.service;

import me.chiqors.minimarket_backend.dto.AccountDTO;
import me.chiqors.minimarket_backend.dto.EmployeeDTO;
import me.chiqors.minimarket_backend.model.Account;
import me.chiqors.minimarket_backend.model.Employee;
import me.chiqors.minimarket_backend.repository.AccountRepository;
import me.chiqors.minimarket_backend.repository.EmployeeRepository;
import me.chiqors.minimarket_backend.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Convert Employee to EmployeeDTO
     * @param employee Employee to be converted
     * @return EmployeeDTO
     */
    public EmployeeDTO convertToEmployeeDTO(Employee employee) {
        AccountDTO accountDTO = convertToAccountDTO(employee.getAccount());
        return new EmployeeDTO(employee.getEmployeeCode(), accountDTO, employee.getName(), employee.getGender(), employee.getBirthDate(), employee.getAddress(), employee.getPhoneNumber(), employee.getCreatedAt(), employee.getUpdatedAt());
    }

    /**
     * Convert Account to AccountDTO
     * @param account Account to be converted
     * @return AccountDTO
     */
    public AccountDTO convertToAccountDTO(Account account) {
        return new AccountDTO(account.getUsername(), account.getEmail(), account.getStatus(), account.getRole(), account.getCreatedAt(), account.getUpdatedAt());
    }

    /**
     * Get All Employees with Pagination and Search By Name
     *
     * @param name Name to be searched
     * @param page Page Number
     * @param size Page Size
     * @return Page<EmployeeDTO> Page of EmployeeDTO
     */
    public Page<EmployeeDTO> getAllEmployees(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Employee> employeePage;

        if (name != null) {
            employeePage = employeeRepository.findAllByNameContaining(name, pageable);
        } else {
            employeePage = employeeRepository.findAll(pageable);
        }

        return employeePage.map(this::convertToEmployeeDTO);
    }

    /**
     * Get Employee By Employee Code
     *
     * @param employeeCode Employee Code
     * @return EmployeeDTO
     */
    public EmployeeDTO getEmployeeByEmployeeCode(String employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeCode);
        if (employee != null) {
            return convertToEmployeeDTO(employee);
        } else {
            return null;
        }
    }

    /**
     * Create Employee with Account
     *
     * @param employeeDTO EmployeeDTO to be created
     * @return EmployeeDTO
     */
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // generate employee code
        String employeeCode = "EMP" + System.currentTimeMillis();

        // create account
        Account account = new Account(employeeDTO.getAccount().getUsername(), employeeDTO.getAccount().getEmail(), employeeDTO.getAccount().getRole(), true);
        accountRepository.save(account);

        // create employee
        Employee employee = new Employee(employeeCode, account, employeeDTO.getName(), employeeDTO.getGender(), employeeDTO.getBirthDate(), employeeDTO.getAddress(), employeeDTO.getPhoneNumber());
        employeeRepository.save(employee);

        return convertToEmployeeDTO(employee);
    }

    /**
     * Update Employee
     *
     * @param employeeDTO EmployeeDTO to be updated
     * @return EmployeeDTO
     */
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findByEmployeeCode(employeeDTO.getEmployeeCode());
        if (employee != null) {
            if (employeeDTO.getAccount() != null) {
                if (employeeDTO.getAccount().getPassword() != null) {
                    // convert into MD5 hash
                    employee.getAccount().setPassword(Helper.getMD5hash(employeeDTO.getAccount().getPassword()));
                }
                if (employeeDTO.getAccount().getEmail() != null) {
                    employee.getAccount().setEmail(employeeDTO.getAccount().getEmail());
                }
            }
            if (employeeDTO.getName() != null) {
                employee.setName(employeeDTO.getName());
            }
            if (employeeDTO.getGender() != null) {
                employee.setGender(employeeDTO.getGender());
            }
            if (employeeDTO.getBirthDate() != null) {
                employee.setBirthDate(employeeDTO.getBirthDate());
            }
            if (employeeDTO.getAddress() != null) {
                employee.setAddress(employeeDTO.getAddress());
            }
            if (employeeDTO.getPhoneNumber() != null) {
                employee.setPhoneNumber(employeeDTO.getPhoneNumber());
            }

            employeeRepository.save(employee);

            return convertToEmployeeDTO(employee);
        } else {
            return null;
        }
    }
}
