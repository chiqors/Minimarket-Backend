package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionDetailDTO {
    @JsonIgnore
    private Long id;

    @JsonProperty("transaction_code")
    private String transactionCode;

    @JsonProperty("product_sku")
    private String productSku;

    private Integer quantity;

    @JsonProperty("snapshot")
    private ProductDTO product;

    public TransactionDetailDTO(String transactionCode, String productSku, Integer quantity, ProductDTO product) {
        this.transactionCode = transactionCode;
        this.productSku = productSku;
        this.quantity = quantity;
        this.product = product;
    }
}
