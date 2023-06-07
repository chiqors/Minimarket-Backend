package me.chiqors.minimarket_backend.controller;

import me.chiqors.minimarket_backend.dto.ProductDTO;
import me.chiqors.minimarket_backend.service.ProductService;
import me.chiqors.minimarket_backend.util.JSONResponse;
import me.chiqors.minimarket_backend.validation.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("${api.prefix}") // cant use ApplicationProperties.API_PREFIX since it is static final
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductValidation productValidation;

    /**
     * Retrieves all products based on optional filtering, sorting, and pagination parameters.
     * @param name    Optional parameter to filter products by title.
     * @param page     Optional parameter to specify the page number of the results.
     * @param size     Optional parameter to specify the number of results per page.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @GetMapping("/products")
    public ResponseEntity<JSONResponse> getAllProducts(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "3") Integer size) {
        try {
            Page<ProductDTO> productDTOList = productService.getAllProducts(name, page, size);
            if (productDTOList != null) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Products retrieved", productDTOList, null);
                return ResponseEntity.ok(jsonResponse);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Products not found", null, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve products", null, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
        }
    }

    /**
     * Retrieves a product by slug.
     *
     * @param slug    The slug of the product to retrieve.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @GetMapping("/products/{slug}")
    public ResponseEntity<JSONResponse> getProductBySlug(@PathVariable("slug") String slug) {
        try {
            ProductDTO productDTO = productService.getProductBySlug(slug);
            if (productDTO != null) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Product retrieved", productDTO, null);
                return ResponseEntity.ok(jsonResponse);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Product not found", null, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve product", null, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
        }
    }

    /**
     * Creates a product.
     *
     * @param productDTO    The product to create.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @PostMapping("/products")
    public ResponseEntity<JSONResponse> createProduct(@RequestBody ProductDTO productDTO) {
        List<String> errors = productValidation.createProductValidation(productDTO);
        if (errors.isEmpty()) {
            try {
                ProductDTO createdProductDTO = productService.createProduct(productDTO);
                if (createdProductDTO != null) {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Product created", createdProductDTO, null);
                    return ResponseEntity.ok(jsonResponse);
                } else {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Failed to create product", null, null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create product", null, null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Failed to create product", null, errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }

    /**
     * Updates a product.
     *
     * @param productDTO    The product to update.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @PutMapping("/products")
    public ResponseEntity<JSONResponse> updateProduct(@RequestBody ProductDTO productDTO) {
        List<String> errors = productValidation.updateProductValidation(productDTO);
        if (errors.isEmpty()) {
            try {
                ProductDTO updatedProductDTO = productService.updateProduct(productDTO);
                if (updatedProductDTO != null) {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Product updated", updatedProductDTO, null);
                    return ResponseEntity.ok(jsonResponse);
                } else {
                    JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Failed to update product", null, null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update product", null, null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Failed to update product", null, errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }

    /**
     * Deletes a product by slug.
     *
     * @param productDTO    The product to delete.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @DeleteMapping("/products")
    public ResponseEntity<JSONResponse> deleteProduct(@RequestBody ProductDTO productDTO) {
        List<String> errors = productValidation.deleteProductValidation(productDTO);
        if (errors.isEmpty()) {
            try {
                productService.deleteProduct(productDTO);
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Product deleted", null, null);
                return ResponseEntity.ok(jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete product", null, null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Failed to delete product", null, errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }
}
