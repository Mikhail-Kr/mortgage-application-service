package ru.mkr.mortgageapplicationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import liquibase.pro.packaged.S;
import org.openapitools.client.api.MortgageCalculatorApi;
import org.openapitools.client.model.MortgageCalculateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mkr.mortgageapplicationservice.customer.Customer;
import ru.mkr.mortgageapplicationservice.customer.CustomerRepository;
import ru.mkr.mortgageapplicationservice.customer.CustomerWithoutId;
import ru.mkr.mortgageapplicationservice.customer.Status;

import java.math.BigDecimal;
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

/*    @Operation(
            operationId = "getCustomer",
            summary = "Найти заявку по ID",
            description = "Return single customer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
*//*                                    @Content(
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
                                    ),*//*
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
    public ResponseEntity getByID(@PathVariable("id") String id) {
        Optional<Customer> userOpt;
        userOpt = customerRepository.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.of(userOpt);
        }
        return ResponseEntity.badRequest().
            body(Collections.singletonMap("error", "Application not exist"));
    }*/

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
            MortgageCalculatorApi mortgageCalculatorApi = new MortgageCalculatorApi();
            MortgageCalculateParams calculateParams= new MortgageCalculateParams();
            calculateParams.setCreditAmount(BigDecimal.valueOf(customerWithId.getCreditAmount()));
            calculateParams.setDurationInMonths(customerWithId.getDurationInMonth());
            BigDecimal monthlyPayment = mortgageCalculatorApi.calculate(calculateParams).getMonthlyPayment();
            if(!customerWithId.poleNoEmpty()) {
                return ResponseEntity.badRequest().
                        body(Collections.singletonMap("error", "one of the fields is null"));
            }
            if(customer.getSalary()/monthlyPayment.doubleValue() >= 2) {
                customerWithId.setStatus(Status.APPROVED);
                customerWithId.setMonthlyPayment(monthlyPayment);
                customerRepository.setCustomerStatus(customerWithId.getId(), Status.APPROVED);
                customerRepository.setCustomerMonthlyPayment(customerWithId.getId(), monthlyPayment);
            } else {
                customerWithId.setStatus(Status.DENIED);
                customerRepository.setCustomerStatus(customerWithId.getId(), Status.DENIED);
            }
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/customer/{id}").
                build(Collections.singletonMap("id", customerWithId.getId()))).body(customerWithId);
        } else {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
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

