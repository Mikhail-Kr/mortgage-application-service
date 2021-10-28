package ru.mkr.mortgageapplicationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mkr.mortgageapplicationservice.customer.Customer;
import ru.mkr.mortgageapplicationservice.customer.CustomerRepository;
import ru.mkr.mortgageapplicationservice.customer.CustomerWithoutId;

import java.util.Optional;

@Tag(name = "Customer", description = "Customer API")
@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
  private final CustomerRepository customerRepository;

  public CustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Operation(
      operationId = "getCustomer",
      summary = "Найти заявку по ID",
      description = "Return single customer",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = {
                  @Content(
                      mediaType = "application/json",
                      examples = {
                          @ExampleObject(
                              value = """
                                  {
                                  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                  "firstName": "Иван",
                                  "secondName": "Иванович",
                                  "lastName": "Иванов",
                                  "passport": "9410123456",
                                  "birthDate": "1990-10-23",
                                  "gender": "MALE",
                                  "salary": 80000,
                                  "creditAmount": 3000000,
                                  "durationInMonths": 120,
                                  "status": "PROCESSING"
                                  }"""
                          )
                      }
                  ),
              }
          ),
          @ApiResponse(
              responseCode = "404",
              description = "Not Found",
              content = {@Content}
          )
      }
  )
  @GetMapping("/customer/{id}")
  ResponseEntity<Optional<Customer>> getCustomer(@PathVariable String id) {
    if(customerRepository.findById(id).isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(customerRepository.findById(id));
    }
  }

  @Operation(
      responses = {
          @ApiResponse(
              responseCode = "200",
              content = {
                  @Content(schema = @Schema(implementation = CustomerWithoutId.class))
              }
          )
      }
  )

  @PostMapping
  ResponseEntity<?> createCustomer(@RequestBody CustomerWithoutId customer) {
    Customer customerWithId = customer.getCustomer(customer);
    if (!isExpected(customer)) {
      customerRepository.save(customerWithId);
      return ResponseEntity.ok(customerWithId);
    } else {
      return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
  }

  boolean isExpected(CustomerWithoutId customerWithoutId) {
    if (customerRepository.findByPassport(customerWithoutId.getPassport()) == null) {
      return false;
    } else {
      return customerRepository.findByPassport(customerWithoutId.getPassport()).getPassport()
          .equals(customerWithoutId.getCustomer(customerWithoutId).getPassport());

      }
    }
  }

