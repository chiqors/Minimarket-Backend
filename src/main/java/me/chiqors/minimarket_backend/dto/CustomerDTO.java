package me.chiqors.minimarket_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chiqors.minimarket_backend.dto.parent.PersonDTO;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class CustomerDTO extends PersonDTO {
    @JsonProperty("customer_code")
    private String customerCode;

    public CustomerDTO(String customerCode, String name, String gender, Date birthDate, String address, String phoneNumber) {
        super(name, gender, birthDate, address, phoneNumber);
        this.customerCode = customerCode;
    }
}
