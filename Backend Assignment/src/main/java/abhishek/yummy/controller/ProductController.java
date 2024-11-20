package abhishek.yummy.controller;

import abhishek.yummy.Entity.Product;
import abhishek.yummy.dto.ProductRequest;
import abhishek.yummy.dto.ProductResponse;
import abhishek.yummy.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody @Valid ProductRequest product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody @Valid ProductRequest product) {
        return ResponseEntity.ok(productService.deleteProduct(product));
    }

    @GetMapping("/priceInRange")
    public ResponseEntity<List<ProductResponse>> getProductInRange(@RequestParam ("from") Double minPrice, @RequestParam ("to") Double maxPrice) {
        return ResponseEntity.ok(productService.getAllProducts(minPrice, maxPrice));
    }
}
