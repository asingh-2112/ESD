package abhishek.yummy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DeleteRequest(

        @NotNull(message = "Customer email is required")
        @Email(message = "Email must be in correct format")
        @JsonProperty("email")
        String email
) {
}
