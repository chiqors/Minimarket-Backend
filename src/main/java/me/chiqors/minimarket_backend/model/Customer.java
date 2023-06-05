package me.chiqors.minimarket_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "account_uuid")
    private Account account;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    // -------------- Out Relationships --------------

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    @OneToOne(mappedBy = "customer")
    private Account accounts;

    // -------------- Methods --------------

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", account=" + account +
                ", customerCode='" + customerCode + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
