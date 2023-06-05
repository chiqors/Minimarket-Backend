package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CouponDTO {
    private String code;

    private String description;

    private Integer value;

    private Integer type;

    @JsonProperty("expired_date")
    private String expiredDate;

    @JsonProperty("created_at")
    private String createdAt;

    public CouponDTO(String code, String description, Integer value, Integer type, String expiredDate, String createdAt) {
        this.code = code;
        this.description = description;
        this.value = value;
        this.type = type;
        this.expiredDate = expiredDate;
        this.createdAt = createdAt;
    }
}
