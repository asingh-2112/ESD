package abhishek.yummy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerResponse (
    @JsonProperty("id")
    Long id,
    @JsonProperty("first_name")
    String firstName,
    @JsonProperty("last_name")
    String lastName,
    @JsonProperty("email")
    String email
){

}