package me.chiqors.minimarket_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chiqors.minimarket_backend.util.DateConverter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "status")
    private Integer status;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_products")
    private Integer totalProducts;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Convert(converter = DateConverter.class)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Convert(converter = DateConverter.class)
    private Date updatedAt;

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
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", totalProducts=" + totalProducts +
                ", transactionDetails=" + transactionDetails +
                '}';
    }
}
