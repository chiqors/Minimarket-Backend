package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TransactionDTO {
    @JsonIgnore
    private Long id;

    @JsonProperty("transaction_code")
    private String transactionCode;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    private Integer status;

    @JsonProperty("total_products")
    private Integer totalProducts;

    @JsonProperty("total_price")
    private Double totalPrice;

    private EmployeeDTO employee;

    private CustomerDTO customer;

    @JsonProperty("transaction_details")
    private List<TransactionDetailDTO> transactionDetails;

    public TransactionDTO(String transactionCode, String createdAt, String updatedAt, Integer status, Integer totalProducts, Double totalPrice, EmployeeDTO employee, CustomerDTO customer, List<TransactionDetailDTO> transactionDetails) {
        this.transactionCode = transactionCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.totalProducts = totalProducts;
        this.totalPrice = totalPrice;
        this.employee = employee;
        this.customer = customer;
        this.transactionDetails = transactionDetails;
    }
}
