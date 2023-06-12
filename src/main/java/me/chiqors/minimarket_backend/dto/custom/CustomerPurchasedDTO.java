package me.chiqors.minimarket_backend.dto.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CustomerPurchasedDTO {
    private String name;

    @JsonProperty("customer_code")
    private String customerCode;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    private String gender;

    @JsonProperty("birth_date")
    private Date birthDate;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("total_purchased")
    private Integer totalPurchased;
}
