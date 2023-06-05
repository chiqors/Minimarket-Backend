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

    private Boolean status;

    @JsonProperty("total_products")
    private Integer totalProducts;

    private EmployeeDTO employee;

    private CustomerDTO customer;

    private CouponDTO coupon;

    @JsonProperty("detail_transactions")
    private List<TransactionDetailDTO> detailTransactions;

    public TransactionDTO(String transactionCode, String createdAt, String updatedAt, Boolean status, Integer totalProducts, EmployeeDTO employeeDTO, CustomerDTO customerDTO, CouponDTO couponDTO, List<TransactionDetailDTO> detailTransactions) {
        this.transactionCode = transactionCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.totalProducts = totalProducts;
        this.employee = employeeDTO;
        this.customer = customerDTO;
        this.coupon = couponDTO;
        this.detailTransactions = detailTransactions;
    }
}
