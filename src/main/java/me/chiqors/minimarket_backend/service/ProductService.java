package me.chiqors.minimarket_backend.service;

import me.chiqors.minimarket_backend.dto.ProductCategoryDTO;
import me.chiqors.minimarket_backend.dto.ProductDTO;
import me.chiqors.minimarket_backend.model.Product;
import me.chiqors.minimarket_backend.model.ProductCategory;
import me.chiqors.minimarket_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    /**
     * Converts a Product entity to a ProductDTO.
     *
     * @param product the Product entity
     * @return the corresponding ProductDTO
     */
    public ProductDTO convertToProductDTO(Product product) {
        ProductCategoryDTO productCategoryDTO = convertToProductCategoryDTO(product.getProductCategory());

        return new ProductDTO(
                product.getSkuCode(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getSlug(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                productCategoryDTO
        );
    }

    /**
     * Converts a ProductCategory entity to a ProductCategoryDTO.
     *
     * @param productCategory the ProductCategory entity
     * @return the corresponding ProductCategoryDTO
     */
    public ProductCategoryDTO convertToProductCategoryDTO(ProductCategory productCategory) {
        return new ProductCategoryDTO(
                productCategory.getSlug(),
                productCategory.getName(),
                productCategory.getSkuCreated(),
                productCategory.getCreatedAt(),
                productCategory.getUpdatedAt()
        );
    }

    /**
     * Retrieves all products with optional name filtering and pagination.
     *
     * @param name the name filter (optional)
     * @param page the page number for pagination
     * @param size the page size for pagination
     * @return the page of ProductDTOs
     */
    public Page<ProductDTO> getAllProducts(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Product> productPage;

        if (name != null) {
            productPage = productRepository.findByNameContainingIgnoreCaseAndIsDeletedIsFalseOrderByCreatedAtDesc(name, pageable);
        } else {
            productPage = productRepository.findByIsDeletedIsFalseOrderByCreatedAtDesc(pageable);
        }

        return productPage.map(this::convertToProductDTO);
    }

    /**
     * Retrieves a product by its SKU code.
     *
     * @param skuCode the SKU code
     * @return the corresponding ProductDTO
     */
    public ProductDTO getProductBySkuCode(String skuCode) {
        Product product = productRepository.findBySkuCodeAndIsDeletedIsFalse(skuCode);

        return convertToProductDTO(product);
    }

    /**
     * Retrieves a product by its slug.
     *
     * @param slug the slug
     * @return the corresponding ProductDTO
     */
    public ProductDTO getProductBySlug(String slug) {
        Product product = productRepository.findBySlugAndIsDeletedIsFalse(slug);

        return convertToProductDTO(product);
    }
}
