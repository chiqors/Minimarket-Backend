package me.chiqors.minimarket_backend.service;

import me.chiqors.minimarket_backend.dto.ProductCategoryDTO;
import me.chiqors.minimarket_backend.model.ProductCategory;
import me.chiqors.minimarket_backend.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     * Retrieves all product categories for form dropdowns.
     *
     * @return the list of ProductCategoryDTOs
     */
    public List<ProductCategoryDTO> getAllProductCategoriesForDropdown() {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAllByOrderByCreatedAtDesc();
        List<ProductCategoryDTO> productCategoryDTOList = new ArrayList<>();

        for (ProductCategory productCategory : productCategoryList) {
            productCategoryDTOList.add(convertToDTO(productCategory));
        }

        return productCategoryDTOList;
    }

    /**
     * Retrieves a product category by its slug.
     *
     * @param slug the slug
     * @return the corresponding ProductCategoryDTO
     */
    public ProductCategoryDTO getProductCategoryBySlug(String slug) {
        ProductCategory productCategory = productCategoryRepository.findBySlug(slug);

        if (productCategory == null) {
            return null;
        }

        return convertToDTO(productCategory);
    }

    /**
     * Validates a product category by its name.
     *
     * @param name the name
     * @return true if the product category exists, false otherwise
     */
    public boolean validateProductCategoryByName(String name) {
        ProductCategory productCategory = productCategoryRepository.findByName(name);

        return productCategory != null;
    }

    /**
     * Validates a product category by its skuCreated.
     *
     * @param skuCreated the skuCreated
     * @return true if the product category exists, false otherwise
     */
    public boolean validateProductCategoryBySkuCreated(String skuCreated) {
        ProductCategory productCategory = productCategoryRepository.findBySkuCreated(skuCreated);

        return productCategory != null;
    }

    /**
     * Validates a product category by its slug.
     *
     * @param slug the slug
     * @return true if the product category exists, false otherwise
     */
    public boolean validateProductCategoryBySlug(String slug) {
        ProductCategory productCategory = productCategoryRepository.findBySlug(slug);

        return productCategory != null;
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

        ProductCategory newProductCategory = new ProductCategory(
                slug,
                productCategory.getName(),
                productCategory.getSkuCreated()
        );

        newProductCategory = productCategoryRepository.save(newProductCategory);

        return convertToDTO(newProductCategory);
    }

    /**
     * Updates a product category.
     *
     * @param productCategoryDTO the ProductCategoryDTO
     * @return the updated ProductCategoryDTO
     */
    public ProductCategoryDTO updateProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory existingProductCategory = productCategoryRepository.findBySlug(productCategoryDTO.getSlug());
        if (existingProductCategory != null) {
            if (productCategoryDTO.getName() != null) {
                existingProductCategory.setName(productCategoryDTO.getName());
            }

            productCategoryRepository.save(existingProductCategory);

            return convertToDTO(existingProductCategory);
        } else {
            return null;
        }
    }
}
