package abhishek.yummy.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductNotFound extends RuntimeException {
    private final String msg;
}
