package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ProductCategoryDTO {
    @JsonIgnore
    private long id;

    private String slug;

    private String name;

    @JsonProperty("sku_created")
    private String skuCreated;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    // Methods

    public ProductCategoryDTO(String slug, String name, String skuCreated, Date createdAt, Date updatedAt) {
        this.slug = slug;
        this.name = name;
        this.skuCreated = skuCreated;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
