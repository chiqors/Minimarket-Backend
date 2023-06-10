package me.chiqors.minimarket_backend.controller;

import me.chiqors.minimarket_backend.dto.ProductCategoryDTO;
import me.chiqors.minimarket_backend.service.ProductCategoryService;
import me.chiqors.minimarket_backend.util.JSONResponse;
import me.chiqors.minimarket_backend.validation.ProductCategoryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("${api.prefix}")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductCategoryValidation productCategoryValidation;

    /**
     * Retrieves all product categories based on optional filtering, sorting, and pagination parameters.
     * @param name    Optional parameter to filter product categories by name.
     * @param page     Optional parameter to specify the page number of the results.
     * @param size     Optional parameter to specify the number of results per page.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @GetMapping("/product-categories")
    public ResponseEntity<JSONResponse> getAllProductCategories(@RequestParam(value = "name", required = false) String name,
                                                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                                @RequestParam(value = "size", required = false, defaultValue = "3") Integer size) {
        try {
            Page<ProductCategoryDTO> productCategoryDTOPage = productCategoryService.getAllProductCategories(name, page, size);
            if (productCategoryDTOPage.isEmpty()) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "No product categories found", null, null);
                return ResponseEntity.ok(jsonResponse);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.FOUND.value(), "Product categories retrieved", productCategoryDTOPage, null);
                return ResponseEntity.ok(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return ResponseEntity.ok(jsonResponse);
        }
    }

    /**
     * Retrieves all product categories for form dropdowns.
     *
     * @return the list of ProductCategoryDTOs
     */
    @GetMapping("/product-categories/dropdown")
    public ResponseEntity<JSONResponse> getAllProductCategoriesForDropdown() {
        try {
            List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getAllProductCategoriesForDropdown();
            if (productCategoryDTOList.isEmpty()) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "No product categories found", null, null);
                return ResponseEntity.ok(jsonResponse);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.FOUND.value(), "Product categories retrieved", productCategoryDTOList, null);
                return ResponseEntity.ok(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return ResponseEntity.ok(jsonResponse);
        }
    }

    /**
     * Retrieves a product category by its slug.
     * @param slug    The slug of the product category.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @GetMapping("/product-categories/{slug}")
    public ResponseEntity<JSONResponse> getProductCategoryBySlug(@PathVariable String slug) {
        ProductCategoryDTO productCategoryDTO = productCategoryService.getProductCategoryBySlug(slug);
        JSONResponse jsonResponse;
        if (productCategoryDTO == null) {
            jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Product category not found", null, null);
        } else {
            jsonResponse = new JSONResponse(HttpStatus.FOUND.value(), "Product category retrieved", productCategoryDTO, null);
        }
        return ResponseEntity.ok(jsonResponse);
    }

    /**
     * Creates a new product category.
     * @param productCategoryDTO    The product category to be created.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @PostMapping("/product-categories")
    public ResponseEntity<JSONResponse> createProductCategory(@RequestBody ProductCategoryDTO productCategoryDTO) {
        List<String> errors = productCategoryValidation.createProductCategoryValidation(productCategoryDTO);
        if (errors.isEmpty()) {
            try {
                ProductCategoryDTO createdProductCategoryDTO = productCategoryService.createProductCategory(productCategoryDTO);
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.CREATED.value(), "Product category created", createdProductCategoryDTO, null);
                return ResponseEntity.ok(jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
                return ResponseEntity.ok(jsonResponse);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Invalid product category", null, errors);
            return ResponseEntity.ok(jsonResponse);
        }
    }

    /**
     * Updates a product category.
     * @param productCategoryDTO    The product category to be updated.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @PutMapping("/product-categories")
    public ResponseEntity<JSONResponse> updateProductCategory(@RequestBody ProductCategoryDTO productCategoryDTO) {
        List<String> errors = productCategoryValidation.updateProductCategoryValidation(productCategoryDTO);
        if (errors.isEmpty()) {
            try {
                ProductCategoryDTO updatedProductCategoryDTO = productCategoryService.updateProductCategory(productCategoryDTO);
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Product category updated", updatedProductCategoryDTO, null);
                return ResponseEntity.ok(jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
                return ResponseEntity.ok(jsonResponse);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Invalid product category", null, errors);
            return ResponseEntity.ok(jsonResponse);
        }
    }
}
