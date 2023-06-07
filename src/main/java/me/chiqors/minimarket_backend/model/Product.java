package me.chiqors.minimarket_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chiqors.minimarket_backend.util.DateConverter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private ProductCategory productCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "slug")
    private String slug;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

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

    @Column(name = "deleted_at")
    private Date deletedAt;

    // -------------- Methods --------------

    public Product(String skuCode, ProductCategory productCategory, String name, String description, Double price, Integer stock, String slug, Boolean isDeleted) {
        this.skuCode = skuCode;
        this.productCategory = productCategory;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.slug = slug;
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", skuCode='" + skuCode + '\'' +
                ", category=" + productCategory +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
