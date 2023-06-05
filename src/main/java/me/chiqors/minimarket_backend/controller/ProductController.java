package me.chiqors.minimarket_backend.controller;

import me.chiqors.minimarket_backend.util.JSONResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("${api.prefix}") // cant use ApplicationProperties.API_PREFIX since it is static final
public class ProductController {

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
        // return sample response
        JSONResponse jsonResponse = new JSONResponse(200, "Products retrieved", null, null);
        return ResponseEntity.ok(jsonResponse);
    }

    /**
     * Retrieves a product based on its slug.
     * @param slug    The slug of the product to be retrieved.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @GetMapping("/product/get/{slug}")
    public ResponseEntity<JSONResponse> getProductBySlug(@PathVariable String slug) {
        // return sample response
        JSONResponse jsonResponse = new JSONResponse(200, "Product retrieved", null, null);
        return ResponseEntity.ok(jsonResponse);
    }

    /**
     * Retrieves a product based on its sku code.
     * @param sku_code    The sku code of the product to be retrieved.
     * @return ResponseEntity containing a JSONResponse and an HTTP status code.
     */
    @GetMapping("/product/{sku_code}")
    public ResponseEntity<JSONResponse> getProductBySkuCode(@PathVariable String sku_code) {
        // return sample response
        JSONResponse jsonResponse = new JSONResponse(200, "Product retrieved", null, null);
        return ResponseEntity.ok(jsonResponse);
    }
}
