package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {
    @JsonIgnore
    private Long id;

    @JsonProperty("customer_code")
    private String customerCode;

    private String name;

    private String gender;

    @JsonProperty("birth_date")
    private String birthDate;

    private String address;

    @JsonProperty("phone_number")
    private String phoneAddress;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    // Additional Properties
    @JsonProperty("account")
    private AccountDTO account;
}
