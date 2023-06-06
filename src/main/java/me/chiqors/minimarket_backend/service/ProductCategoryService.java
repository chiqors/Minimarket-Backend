package me.chiqors.minimarket_backend.service;

import me.chiqors.minimarket_backend.dto.ProductCategoryDTO;
import me.chiqors.minimarket_backend.model.ProductCategory;
import me.chiqors.minimarket_backend.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    /**
     * Converts a ProductCategory entity to a ProductCategoryDTO.
     *
     * @param productCategory the ProductCategory entity
     * @return the corresponding ProductCategoryDTO
     */
    public ProductCategoryDTO convertToDTO(ProductCategory productCategory) {
        return new ProductCategoryDTO(productCategory.getSlug(), productCategory.getName(), productCategory.getSkuCreated(), productCategory.getCreatedAt(), productCategory.getUpdatedAt());
    }

    /**
     * Retrieves all product categories with optional name filtering and pagination.
     *
     * @param name the name filter (optional)
     * @param page the page number for pagination
     * @param size the page size for pagination
     * @return the page of ProductCategoryDTOs
     */
    public Page<ProductCategoryDTO> getAllProductCategories(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<ProductCategory> productCategoryPage;

        if (name != null) {
            productCategoryPage = productCategoryRepository.findByNameContainingIgnoreCaseOrderByCreatedAtDesc(name, pageable);
        } else {
            productCategoryPage = productCategoryRepository.findAllByOrderByCreatedAtDesc(pageable);
        }

        return productCategoryPage.map(this::convertToDTO);
    }

    /**
     * Retrieves a product category by its slug.
     *
     * @param slug the slug
     * @return the corresponding ProductCategoryDTO
     */
    public ProductCategoryDTO getProductCategoryBySlug(String slug) {
        ProductCategory productCategory = productCategoryRepository.findBySlug(slug);

        return convertToDTO(productCategory);
    }

    /**
     * Creates a new product category.
     *
     * @param productCategory the ProductCategoryDTO
     * @return the created ProductCategoryDTO
     */
    public ProductCategoryDTO createProductCategory(ProductCategoryDTO productCategory) {
        // convert name into slug
        String slug = productCategory.getName().toLowerCase().replaceAll("\\s+", "-");
        // convert skuCreated into uppercase
        String skuCreated = productCategory.getSkuCreated().toUpperCase();

        ProductCategory newProductCategory = new ProductCategory(
                slug,
                productCategory.getName(),
                skuCreated
        );

        newProductCategory = productCategoryRepository.save(newProductCategory);

        return convertToDTO(newProductCategory);
    }

    /**
     * Updates a product category.
     *
     * @param productCategory the ProductCategoryDTO
     * @return the updated ProductCategoryDTO
     */
    public ProductCategoryDTO updateProductCategory(ProductCategoryDTO productCategory) {
        ProductCategory existingProductCategory = productCategoryRepository.findBySlug(productCategory.getSlug());

        existingProductCategory.setName(productCategory.getName());
        existingProductCategory.setSkuCreated(productCategory.getSkuCreated());

        existingProductCategory = productCategoryRepository.save(existingProductCategory);

        return convertToDTO(existingProductCategory);
    }

    /**
     * Deletes a product category.
     *
     * @param productCategory the ProductCategoryDTO
     */
    public void deleteProductCategory(ProductCategoryDTO productCategory) {
        ProductCategory existingProductCategory = productCategoryRepository.findBySlug(productCategory.getSlug());

        productCategoryRepository.delete(existingProductCategory);
    }
}
