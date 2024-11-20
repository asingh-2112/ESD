package abhishek.yummy.service;

import abhishek.yummy.Entity.Customer;
import abhishek.yummy.dto.*;
import abhishek.yummy.exception.CustomerNotFound;
import abhishek.yummy.loginhelper.EncryptionService;
import abhishek.yummy.loginhelper.JWThelper;
import abhishek.yummy.mapper.CustomerMapper;
import abhishek.yummy.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo repo;
    private final CustomerMapper mapper;
    private final EncryptionService encryptionService;
    private final JWThelper jwThelper;

    public String createCustomer(CustomerRequest request) {
        Customer customer = mapper.toEntity(request);
        customer.setPassword(encryptionService.encode(customer.getPassword()));
        repo.save(customer);
        return "Customer created";
    }

    public Customer getCustomer(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CustomerNotFound(
                   format("No customer found by the given ID : %s", id)
                ));
    }

    public String updateCustomer(String token, UpdateRequest request) {
        if(!jwThelper.validateToken(token, request.email())){
            return "Invalid token";
        }

        Customer customer = loginCustomer(request.email());

        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setPassword(encryptionService.encode(request.password()));
        repo.save(customer);
        return "Customer updated";
    }

    public String deleteCustomer(String token, DeleteRequest request) {
        if(!jwThelper.validateToken(token, request.email())){
            return "Invalid token";
        }
        Customer customer = loginCustomer(request.email());
        repo.delete(customer);
        return "Customer deleted";
    }

    public Customer loginCustomer(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFound(
                        format("No customer found by the given ID : %s", email)
                ));
    }

    public CustomerResponse retrieveCustomer(Long id) {
        Customer customer = getCustomer(id);
        return mapper.toCustomerResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return repo.findAll().stream()
                .map(mapper::toCustomerResponse) // map Customer to CustomerResponse
                .collect(Collectors.toList());
    }

    public  String login(LoginRequest request) {
        Customer customer = loginCustomer(request.email());
        if(!encryptionService.validates(request.password(), customer.getPassword())) {
            return "Wrong password or Email";
        }
        return jwThelper.generateToken(request.email());
    }

}
