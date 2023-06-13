package me.chiqors.minimarket_backend.dto.custom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class MostPurchasedProductDTO {
    @JsonProperty("sku_code")
    private String skuCode;

    private String name;

    private String category;

    @JsonProperty("total_purchased")
    private Integer totalPurchased;

    private Double price;

    private Integer stock;
}
