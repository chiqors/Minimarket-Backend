package me.chiqors.minimarket_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chiqors.minimarket_backend.model.parent.Person;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends Person {
    @Column(name = "customer_code")
    private String customerCode;

    public Customer(String customerCode, String name, String gender, Date birthDate, String address, String phoneNumber) {
        super(name, gender, birthDate, address, phoneNumber);
        this.customerCode = customerCode;
    }
}
