package abhishek.yummy.service;

import abhishek.yummy.Entity.Product;
import abhishek.yummy.dto.ProductRequest;
import abhishek.yummy.dto.ProductResponse;
import abhishek.yummy.exception.ProductNotFound;
import abhishek.yummy.mapper.ProductMapper;
import abhishek.yummy.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper mapper;
    private final ProductRepo repo;


    public String createProduct(ProductRequest request) {
        Product product = mapper.toEntity(request);
        repo.save(product);
        return "Product Added Successfully";
    }

    public ProductResponse getProduct(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ProductNotFound("Product not found"));
        return mapper.toProductResponse(product);
    }

    public Product validate(String productName) {
        return repo.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFound("Product not found"));
    }

    public String updateProduct(ProductRequest request) {
        Product product = validate(request.productName());
        product.setPrice(request.price());
        repo.save(product);
        return "Product Updated Successfully";
    }

    public String deleteProduct(ProductRequest request) {
        Product product = validate(request.productName());
        repo.delete(product);
        return "Product Deleted Successfully";
    }

    public List<ProductResponse> getAllProducts(Double minPrice, Double maxPrice) {
        return repo.findByPriceGreaterThanAndPriceLessThanOrderByPriceAsc(minPrice, maxPrice);
    }
}
