package me.chiqors.minimarket_backend.validation;

import me.chiqors.minimarket_backend.dto.ProductDTO;
import me.chiqors.minimarket_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidation {
    @Autowired
    private ProductService productService;

    /**
     * Validates the product creation request.
     *
     * @param productDTO the product creation request
     * @return a list of errors
     */
    public List<String> createProductValidation(ProductDTO productDTO) {
        List<String> errors = new ArrayList<>();

        if (productDTO.getName().isEmpty()) {
            errors.add("Name is required.");
        } else if (productService.validateProductByName(productDTO.getName())) {
            errors.add("Name is already taken.");
        }

        if (productDTO.getDescription() == null) {
            errors.add("Description is required.");
        } else {
            if (productDTO.getDescription().length() < 10) {
                errors.add("Description must be at least 10 characters long.");
            }
        }

        if (productDTO.getPrice() == null) {
            errors.add("Price is required.");
        } else if (productDTO.getPrice() < 0) {
            errors.add("Price must be greater than 0.");
        }

        if (productDTO.getStock() == null) {
            errors.add("Stock is required.");
        } else if (productDTO.getStock() < 0) {
            errors.add("Stock must be greater than 0.");
        }

        if (productDTO.getProductCategory() == null) {
            errors.add("Product Category is required.");
        } else {
            if (!productService.validateProductCategoryBySku(productDTO.getProductCategory().getSkuCreated())) {
                errors.add("SKU Code for Product Category does not exist.");
            }
        }

        return errors;
    }

    /**
     * Validates the product update request.
     *
     * @param productDTO the product update request
     * @return a list of errors
     */
    public List<String> updateProductValidation(ProductDTO productDTO) {
        List<String> errors = new ArrayList<>();

        // at least one of them exist.. name, description, price, stock, and skuCreated
        if (productDTO.getName() == null && productDTO.getDescription() == null && productDTO.getPrice() == null && productDTO.getStock() == null && productDTO.getProductCategory() == null) {
            errors.add("At least one of the following is required: name, description, price, stock, and skuCreated.");
        }

        // this is required for pointing to the product
        if (productDTO.getSlug() == null) {
            errors.add("Slug is required.");
        } else {
            if (!productService.validateProductBySlug(productDTO.getSlug())) {
                errors.add("Slug for Product does not exist.");
            }
        }

        if (productDTO.getName() != null) {
            if (productDTO.getName().length() < 3) {
                errors.add("Name must be at least 3 characters long.");
            }
        }

        if (productDTO.getDescription() != null) {
            if (productDTO.getDescription().length() < 10) {
                errors.add("Description must be at least 10 characters long.");
            }
        }

        if (productDTO.getPrice() != null) {
            if (productDTO.getPrice() < 0) {
                errors.add("Price must be greater than 0.");
            }
        }

        if (productDTO.getStock() != null) {
            if (productDTO.getStock() < 0) {
                errors.add("Stock must be greater than 0.");
            }
        }

        if (productDTO.getProductCategory() != null) {
            if (!productService.validateProductCategoryBySku(productDTO.getProductCategory().getSkuCreated())) {
                errors.add("Product Category does not exist.");
            }
        }

        return errors;
    }

    /**
     * Validates the product deletion request.
     *
     * @param productDTO the product deletion request
     * @return a list of errors
     */
    public List<String> deleteProductValidation(ProductDTO productDTO) {
        List<String> errors = new ArrayList<>();

        if (productDTO.getSlug() == null) {
            errors.add("Slug is required.");
        } else {
            if (!productService.validateProductBySlug(productDTO.getSlug())) {
                errors.add("Slug for Product does not exist.");
            }
        }

        return errors;
    }
}
