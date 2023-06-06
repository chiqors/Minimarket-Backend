package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chiqors.minimarket_backend.dto.parent.PersonDTO;

@Getter @Setter
@NoArgsConstructor
public class EmployeeDTO extends PersonDTO {
    @JsonProperty("employee_code")
    private String employeeCode;

    // Additional Properties

    @JsonProperty("account")
    private AccountDTO account;

    // Methods

    public EmployeeDTO(String employeeCode, AccountDTO account) {
        this.employeeCode = employeeCode;
        this.account = account;
    }
}
