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
@Table(name = "employees")
public class Employee extends Person {
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "employee_code")
    private String employeeCode;

    public Employee(String employeeCode, Account account, String name, String gender, Date birthDate, String address, String phoneNumber) {
        super(name, gender, birthDate, address, phoneNumber);
        this.employeeCode = employeeCode;
        this.account = account;
    }
}
