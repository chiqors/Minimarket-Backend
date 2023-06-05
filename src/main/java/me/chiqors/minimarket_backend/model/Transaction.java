package me.chiqors.minimarket_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_code")
    private String transactionCode;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "coupon_code")
    private Coupon coupon;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_products")
    private Integer totalProducts;

    // -------------- Out Relationships --------------

    @OneToMany(mappedBy = "transaction")
    private List<TransactionDetail> transactionDetails;

    // -------------- Methods --------------

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionCode='" + transactionCode + '\'' +
                ", employee=" + employee +
                ", customer=" + customer +
                ", coupon=" + coupon +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", totalProducts=" + totalProducts +
                ", transactionDetails=" + transactionDetails +
                '}';
    }
}
