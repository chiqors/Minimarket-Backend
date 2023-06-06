package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ProductDTO {
    @JsonIgnore
    private long id;

    @JsonProperty("sku_code")
    private String skuCode;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private String slug;

    @JsonIgnore
    private Boolean isDeleted;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonIgnore
    private Date deletedAt;

    // -------------- Out Relationships --------------

    @JsonProperty("product_category")
    private ProductCategoryDTO category;

    // -------------- Constructor --------------

    public ProductDTO(String skuCode, String name, String description, Double price, Integer stock, String slug, Date createdAt, Date updatedAt, ProductCategoryDTO category) {
        this.skuCode = skuCode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.slug = slug;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
    }
}
