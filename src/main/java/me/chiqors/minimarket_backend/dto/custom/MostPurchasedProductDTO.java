package me.chiqors.minimarket_backend.dto.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MostPurchasedProductDTO {
    @JsonProperty("sku_code")
    private String skuCode;

    private String name;

    private String category;

    @JsonProperty("total_purchased")
    private Integer totalPurchased;

    // -------------- Constructor --------------

    public MostPurchasedProductDTO(String skuCode, String name, String category, Integer totalPurchased) {
        this.skuCode = skuCode;
        this.name = name;
        this.category = category;
        this.totalPurchased = totalPurchased;
    }
}
