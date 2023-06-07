package me.chiqors.minimarket_backend.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class AccountFormDTO {
    @JsonIgnore
    private Long id;

    private String username;

    private String password;

    private String email;

    private Boolean status;

    private Integer role;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    public AccountFormDTO(String username, String email, Boolean status, Integer role, Date createdAt, Date updatedAt) {
        this.username = username;
        this.email = email;
        this.status = status;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
