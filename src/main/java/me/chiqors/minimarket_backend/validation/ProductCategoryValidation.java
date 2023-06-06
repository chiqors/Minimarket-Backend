package me.chiqors.minimarket_backend.validation;

import me.chiqors.minimarket_backend.dto.ProductCategoryDTO;
import me.chiqors.minimarket_backend.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCategoryValidation {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * Validates the product category creation request.
     *
     * @param productCategoryDTO the product category creation request
     * @return a list of errors
     */
    public List<String> createProductCategoryValidation(ProductCategoryDTO productCategoryDTO) {
        List<String> errors = new ArrayList<>();

        if (productCategoryDTO.getName().isEmpty()) {
            errors.add("Name is required.");
        } else if (productCategoryService.validateProductCategoryByName(productCategoryDTO.getName())) {
            errors.add("Name is already taken.");
        }

        if (productCategoryDTO.getSkuCreated().isEmpty()) {
            errors.add("SKU created is required.");
        } else if (productCategoryDTO.getSkuCreated().length() < 3 || productCategoryDTO.getSkuCreated().length() > 4) {
            errors.add("SKU created must be 3-4 characters long.");
        } else if (!productCategoryDTO.getSkuCreated().matches("[a-zA-Z]+")) {
            errors.add("SKU created must be alphabetical.");
        } else if (productCategoryService.validateProductCategoryBySkuCreated(productCategoryDTO.getSkuCreated())) {
            errors.add("SKU created is already taken.");
        }

        return errors;
    }

    /**
     * Validates the product category update request.
     *
     * @param productCategoryDTO the product category update request
     * @return a list of errors
     */
    public List<String> updateProductCategoryValidation(ProductCategoryDTO productCategoryDTO) {
        List<String> errors = new ArrayList<>();

        if (productCategoryDTO.getSlug() == null) {
            errors.add("Slug is required.");
        } else {
            if (!productCategoryService.validateProductCategoryBySlug(productCategoryDTO.getSlug())) {
                errors.add("Slug for Product category does not exist.");
            }
        }

        if (productCategoryDTO.getName().isEmpty()) {
            errors.add("Name is required.");
        } else if (productCategoryService.validateProductCategoryByName(productCategoryDTO.getName())) {
            errors.add("Name is already taken.");
        }

        return errors;
    }
}
