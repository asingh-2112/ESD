package abhishek.yummy.controller;

import abhishek.yummy.Entity.Customer;
import abhishek.yummy.dto.CustomerRequest;
import abhishek.yummy.dto.CustomerResponse;
import abhishek.yummy.dto.DeleteRequest;
import abhishek.yummy.dto.UpdateRequest;
import abhishek.yummy.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/allCustomer")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.retrieveCustomer(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestHeader ("Authorization") String authHeader, @RequestBody @Valid UpdateRequest request){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
        }
        return ResponseEntity.ok(customerService.updateCustomer(authHeader, request));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCustomer(@RequestHeader ("Authorization") String authHeader, @RequestBody @Valid DeleteRequest request){
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
        }
        return ResponseEntity.ok(customerService.deleteCustomer(authHeader, request));
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }
}
