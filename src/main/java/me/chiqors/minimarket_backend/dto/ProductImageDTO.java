package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductImageDTO {
    @JsonIgnore
    private long id;

    private String product_sku;

    @JsonProperty("image_name")
    private String imageName;

    @JsonProperty("image_type")
    private String imageType;

    public ProductImageDTO(String product_sku, String imageName, String imageType) {
        this.product_sku = product_sku;
        this.imageName = imageName;
        this.imageType = imageType;
    }
}
