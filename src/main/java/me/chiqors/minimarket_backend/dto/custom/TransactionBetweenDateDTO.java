package me.chiqors.minimarket_backend.dto.custom;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionBetweenDateDTO {
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

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("customer_name")
    private String customerName;

    public TransactionBetweenDateDTO(String transactionCode, String createdAt, String updatedAt, Integer status, Integer totalProducts, Double totalPrice, String employeeName, String customerName) {
        this.transactionCode = transactionCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.totalProducts = totalProducts;
        this.totalPrice = totalPrice;
        this.employeeName = employeeName;
        this.customerName = customerName;
    }
}
