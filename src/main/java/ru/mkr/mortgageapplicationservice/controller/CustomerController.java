package ru.mkr.mortgageapplicationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.openapitools.client.api.MortgageCalculatorApi;
import org.openapitools.client.model.MortgageCalculateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mkr.mortgageapplicationservice.customer.Customer;
import ru.mkr.mortgageapplicationservice.customer.CustomerRepository;
import ru.mkr.mortgageapplicationservice.customer.Status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
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
                      schema = @Schema(
                          implementation = Customer.class
                      )
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
  public ResponseEntity<?> getByID(@PathVariable String id) {
    if (id.length() != 36) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", "invalid id"));
    }
    Optional<Customer> savedCustomer;
    savedCustomer = customerRepository.findById(id);
    if (savedCustomer.isPresent()) {
      return ResponseEntity.of(savedCustomer);
    }
    return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
  }

  @Operation(
      operationId = "createCustomer",
      summary = "Оформить заявку на ипотеку",
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
      if (!customerWithId.poleNoZero()) {
        return ResponseEntity.badRequest().
            body(Collections.singletonMap("error", "one of the fields is zero"));
      }
      MortgageCalculatorApi mortgageCalculatorApi = new MortgageCalculatorApi();
      MortgageCalculateParams calculateParams = new MortgageCalculateParams();
      calculateParams.setCreditAmount(customerWithId.getCreditAmount());
      calculateParams.setDurationInMonths(customerWithId.getDurationInMonths());
      BigDecimal monthlyPayment = mortgageCalculatorApi.calculate(calculateParams).getMonthlyPayment();
      if (!customerWithId.poleNoEmpty()) {
        return ResponseEntity.badRequest().
            body(Collections.singletonMap("error", "one of the fields is null"));
      }
      if (customer.getSalary().divide(monthlyPayment, 0, RoundingMode.DOWN).compareTo(new BigDecimal(2)) == 1) {
        customerWithId.setStatus(Status.APPROVED);
        customerWithId.setMonthlyPayment(monthlyPayment);
        customerRepository.save(customerWithId);
      } else {
        customerWithId.setStatus(Status.DENIED);
        customerRepository.save(customerWithId);
      }
      return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/customer/{id}").
          build(Collections.singletonMap("id", customerWithId.getId()))).body(customerWithId);
    } else {
      return new ResponseEntity<String>(HttpStatus.CONFLICT);
    }
  }

  boolean isExpected(CustomerWithoutId customerWithoutId) {
    if (customerRepository.findByFirstNameAndSecondNameAndLastNameAndPassport(customerWithoutId.getFirstName(),
        customerWithoutId.getSecondName(), customerWithoutId.getLastName(),
        customerWithoutId.getPassport()) == null) {
      return false;
    } else {
      return true;

    }
  }
}

