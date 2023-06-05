package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SupplierDTO {
    @JsonIgnore
    private long id;

    @JsonProperty("supplier_code")
    private String supplierCode;

    private String name;

    private String address;

    private String phoneNumber;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    public SupplierDTO(String supplierCode, String name, String address, String phoneNumber, String createdAt, String updatedAt) {
        this.supplierCode = supplierCode;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}