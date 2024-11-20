package abhishek.yummy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("product_name")
        String productName,
        @JsonProperty("price")
        Double price
) {
}
