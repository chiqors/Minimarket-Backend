package me.chiqors.minimarket_backend.service;

import me.chiqors.minimarket_backend.dto.ProductCategoryDTO;
import me.chiqors.minimarket_backend.dto.ProductDTO;
import me.chiqors.minimarket_backend.model.Product;
import me.chiqors.minimarket_backend.model.ProductCategory;
import me.chiqors.minimarket_backend.repository.ProductCategoryRepository;
import me.chiqors.minimarket_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

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
        if (product != null) {
            return convertToProductDTO(product);
        } else {
            return null;
        }
    }

    /**
     * Retrieves a product by its slug.
     *
     * @param slug the slug
     * @return the corresponding ProductDTO
     */
    public ProductDTO getProductBySlug(String slug) {
        Product product = productRepository.findBySlugAndIsDeletedIsFalse(slug);
        if (product != null) {
            return convertToProductDTO(product);
        } else {
            return null;
        }
    }

    /**
     * Validates a product by name.
     *
     * @param name the name
     * @return true if the product exists, false otherwise
     */
    public boolean validateProductByName(String name) {
        return productRepository.existsByName(name);
    }

    /**
     * Validates a product by slug.
     *
     * @param slug the slug
     * @return true if the product exists, false otherwise
     */
    public boolean validateProductBySlug(String slug) {
        return productRepository.existsBySlug(slug);
    }

    /**
     * Validates a product by SKU code.
     *
     * @param skuCreated the SKU code created from the product category
     * @return true if the product exists, false otherwise
     */
    public boolean validateProductCategoryBySku(String skuCreated) {
        return productCategoryRepository.existsBySkuCreated(skuCreated);
    }

    /**
     * Creates a product.
     *
     * @param productDTO the ProductDTO
     * @return the created ProductDTO
     */
    public ProductDTO createProduct(ProductDTO productDTO) {
        // generate slug
        String slug = productDTO.getName().toLowerCase().replaceAll("\\s+", "-");

        // generate SKU code
        // format: <skuCreated>-P<DDMMYYYYHHMMSS>
        String skuCreated = productDTO.getProductCategory().getSkuCreated();
        String skuCode = skuCreated + "-P" + System.currentTimeMillis();

        // get product category from skuCreated in product categories table
        ProductCategory productCategory = productCategoryRepository.findBySkuCreated(skuCreated);

        Product product = new Product(
                skuCode,
                productCategory,
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getStock(),
                slug,
                false
        );

        productRepository.save(product);

        return convertToProductDTO(product);
    }

    /**
     * Updates a product.
     *
     * @param productDTO the ProductDTO
     * @return the updated ProductDTO
     */
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product existProduct = productRepository.findBySlugAndIsDeletedIsFalse(productDTO.getSlug());
        if (existProduct != null) {
            if (productDTO.getProductCategory() != null) {
                ProductCategory productCategory = productCategoryRepository.findBySkuCreated(productDTO.getProductCategory().getSkuCreated());
                existProduct.setProductCategory(productCategory);
            }
            if (productDTO.getName() != null) {
                existProduct.setName(productDTO.getName());
            }
            if (productDTO.getDescription() != null) {
                existProduct.setDescription(productDTO.getDescription());
            }
            if (productDTO.getPrice() != null) {
                existProduct.setPrice(productDTO.getPrice());
            }
            if (productDTO.getStock() != null) {
                existProduct.setStock(productDTO.getStock());
            }

            productRepository.save(existProduct);

            return convertToProductDTO(existProduct);
        } else {
            return null;
        }
    }

    /**
     * Deletes a product.
     *
     * @param productDTO the ProductDTO
     */
    public void deleteProduct(ProductDTO productDTO) {
        Product existProduct = productRepository.findBySlugAndIsDeletedIsFalse(productDTO.getSlug());
        if (existProduct != null) {
            existProduct.setIsDeleted(true);
            existProduct.setDeletedAt(new Date());

            productRepository.save(existProduct);
        }
    }
}
