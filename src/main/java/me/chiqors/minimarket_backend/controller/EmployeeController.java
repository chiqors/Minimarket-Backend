package me.chiqors.minimarket_backend.controller;

import me.chiqors.minimarket_backend.dto.EmployeeDTO;
import me.chiqors.minimarket_backend.service.EmployeeService;
import me.chiqors.minimarket_backend.util.JSONResponse;
import me.chiqors.minimarket_backend.validation.EmployeeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("${api.prefix}")
public class EmployeeController {
    @Autowired
    private EmployeeValidation employeeValidation;
    @Autowired
    private EmployeeService employeeService;

    /**
     * Get all employees with pagination and search by name
     *
     * @param name  (optional) search by name
     * @param page  (optional) page number
     * @param size  (optional) page size
     * @return ResponseEntity<JSONResponse> with status and JSONResponse body
     */
    @GetMapping("/employees")
    public ResponseEntity<JSONResponse> getAllEmployees(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false, defaultValue = "1") Integer page,
                                                        @RequestParam(required = false, defaultValue = "3") Integer size) {
        try {
            Page<EmployeeDTO> employeeDTOPage = employeeService.getAllEmployees(name, page, size);
            if (employeeDTOPage != null) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.FOUND.value(), "Employees found", employeeDTOPage, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.FOUND);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Employees not found", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get employee by employeeCode
     *
     * @param employeeCode employeeCode of employee
     * @return ResponseEntity<JSONResponse> with status and JSONResponse body
     */
    @GetMapping("/employees/{employeeCode}")
    public ResponseEntity<JSONResponse> getEmployeeByEmployeeCode(@PathVariable String employeeCode) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeByEmployeeCode(employeeCode);
            if (employeeDTO != null) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.FOUND.value(), "Employee found", employeeDTO, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.FOUND);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Employee not found", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create new employee
     *
     * @param employeeDTO employeeDTO object
     * @return ResponseEntity<JSONResponse> with status and JSONResponse body
     */
    @PostMapping("/employees")
    public ResponseEntity<JSONResponse> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        List<String> errors = employeeValidation.createEmployeeValidation(employeeDTO);
        if (errors.isEmpty()) {
            try {
                EmployeeDTO newEmployeeDTO = employeeService.createEmployee(employeeDTO);
                if (newEmployeeDTO != null) {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.CREATED.value(), "Employee created", newEmployeeDTO, null);
                    return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
                } else {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Employee not created", null, null);
                    return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Employee not created", null, errors);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update employee by employeeCode
     *
     * @param employeeDTO employeeDTO object
     * @return ResponseEntity<JSONResponse> with status and JSONResponse body
     */
    @PutMapping("/employees")
    public ResponseEntity<JSONResponse> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        List<String> errors = employeeValidation.updateEmployeeValidation(employeeDTO);
        if (errors.isEmpty()) {
            try {
                EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployee(employeeDTO);
                if (updatedEmployeeDTO != null) {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Employee updated", updatedEmployeeDTO, null);
                    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
                } else {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Employee not updated", null, null);
                    return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Employee not updated", null, errors);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
