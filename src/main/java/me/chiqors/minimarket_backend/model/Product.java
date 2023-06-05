package me.chiqors.minimarket_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sku_code")
    private String skuCode;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "expired_date")
    private Date expiredDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    // -------------- Out Relationships --------------

    @OneToOne(mappedBy = "product")
    private ProductImage productImage;

    @OneToOne(mappedBy = "product")
    private ProductCategory productCategory;

    // -------------- Methods --------------

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", skuCode='" + skuCode + '\'' +
                ", category=" + category +
                ", supplier=" + supplier +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", expiredDate=" + expiredDate +
                ", price=" + price +
                ", stock=" + stock +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
