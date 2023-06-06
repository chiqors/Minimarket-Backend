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
@Table(name = "product_categories")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "name")
    private String name;

    @Column(name = "sku_created")
    private String skuCreated;

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

    // -------------- Methods --------------

    public ProductCategory(String slug, String name, String skuCreated) {
        this.slug = slug;
        this.name = name;
        this.skuCreated = skuCreated;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", slug='" + slug + '\'' +
                ", skuCreated='" + skuCreated + '\'' +
                '}';
    }
}
