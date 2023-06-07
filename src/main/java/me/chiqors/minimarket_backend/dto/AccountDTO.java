package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AccountDTO {
    @JsonIgnore
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private Boolean status;

    private Integer role;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    public AccountDTO(String username, String email, Boolean status, Integer role, Date createdAt, Date updatedAt) {
        this.username = username;
        this.email = email;
        this.status = status;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
