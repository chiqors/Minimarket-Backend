package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    @JsonIgnore
    private Long id;

    private String name;

    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private Boolean status;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    // Additional Properties

    @JsonProperty("account")
    private AccountDTO account;
}
