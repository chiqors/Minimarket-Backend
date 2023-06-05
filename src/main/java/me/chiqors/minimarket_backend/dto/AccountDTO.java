package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AccountDTO {
    @JsonIgnore
    private UUID id;

    private String username;

    private String email;

    private Boolean status;

    private Integer role;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    public AccountDTO(String username, String email, Boolean status, Integer role, String createdAt, String updatedAt) {
        this.username = username;
        this.email = email;
        this.status = status;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
