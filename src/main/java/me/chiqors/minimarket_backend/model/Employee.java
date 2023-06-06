package me.chiqors.minimarket_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chiqors.minimarket_backend.model.parent.Person;

import javax.persistence.*;

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
}
