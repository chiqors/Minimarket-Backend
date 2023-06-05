package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDTO {
    @JsonIgnore
    private long id;

    @JsonProperty("sku_code")
    private String skuCode;

    private String name;

    private String description;

    private Integer stock;

    private Double price;

    @JsonProperty("expired_date")
    private String expiredDate;

    @JsonIgnore
    private Boolean deleted;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonIgnore
    private String deletedAt;

    // -------------- Out Relationships --------------

    @JsonProperty("product_category")
    private ProductCategoryDTO category;

    @JsonProperty("product_images")
    private List<ProductImageDTO> productImages;

    // -------------- Constructor --------------

    public ProductDTO(String skuCode, ProductCategoryDTO category, String name, String description, int stock, double price, String expiredDate, String createdAt, String updatedAt) {
        this.skuCode = skuCode;
        this.category = category;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.expiredDate = expiredDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
