package me.chiqors.minimarket_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String code;

    @Column(name = "value")
    private Integer value;

    @Column(name = "type")
    private Integer type;

    @Column(name = "expired_date")
    private Date expiredDate;

    @Column(name = "created_at")
    private Date createdAt;

    // -------------- Methods --------------

    @Override
    public String toString() {
        return "Coupon{" +
                "code='" + code + '\'' +
                ", value=" + value +
                ", type=" + type +
                ", expiredDate=" + expiredDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
