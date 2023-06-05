package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductCategoryDTO {
    @JsonIgnore
    private long id;

    private String name;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    private String slug;

    @JsonProperty("sku_created")
    private String skuCreated;

    public ProductCategoryDTO(String name, String createdAt, String updatedAt, String slug, String skuCreated) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.slug = slug;
        this.skuCreated = skuCreated;
    }
}
