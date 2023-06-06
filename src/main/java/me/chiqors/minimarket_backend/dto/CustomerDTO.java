package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chiqors.minimarket_backend.dto.parent.PersonDTO;

@Getter @Setter
@NoArgsConstructor
public class CustomerDTO extends PersonDTO {
    @JsonProperty("customer_code")
    private String customerCode;

    // Methods

    public CustomerDTO(String customerCode) {
        this.customerCode = customerCode;
    }
}
