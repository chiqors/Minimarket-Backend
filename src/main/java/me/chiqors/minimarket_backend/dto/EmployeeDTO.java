package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chiqors.minimarket_backend.dto.parent.PersonDTO;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class EmployeeDTO extends PersonDTO {
    @JsonProperty("employee_code")
    private String employeeCode;

    // Additional Properties

    @JsonProperty("account")
    private AccountDTO account;

    // Methods

    public EmployeeDTO(String employeeCode, AccountDTO account, String name, String gender, Date birthdate, String address, String phoneNumber, Date createdAt, Date updatedAt) {
        super(name, gender, birthdate, address, phoneNumber, createdAt, updatedAt);
        this.employeeCode = employeeCode;
        this.account = account;
    }
}
