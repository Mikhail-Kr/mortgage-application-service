package ru.mkr.mortgageapplicationservice.controller;

import lombok.Data;
import ru.mkr.mortgageapplicationservice.customer.Customer;
import ru.mkr.mortgageapplicationservice.customer.Gender;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerWithoutId {

  private String firstName;
  private String secondName;
  private String lastName;
  private String passport;
  private LocalDate birthDate;
  @Enumerated(EnumType.STRING)
  private Gender gender;
  private BigDecimal salary;
  private BigDecimal creditAmount;
  private int durationInMonths;

  public CustomerWithoutId() {

  }

  public CustomerWithoutId(String firstName, String secondName, String lastName,
                           String passport, LocalDate birthDate, Gender gender,
                           BigDecimal salary, BigDecimal creditAmount, int durationInMonth) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.lastName = lastName;
    this.passport = passport;
    this.birthDate = birthDate;
    this.gender = gender;
    this.salary = salary;
    this.creditAmount = creditAmount;
    this.durationInMonths = durationInMonth;
  }


  public Customer getCustomer(CustomerWithoutId customer) {
    return new Customer(generateId(), firstName, secondName, lastName, passport,
        birthDate, gender, salary, creditAmount, durationInMonths);
  }

  private String generateId() {
    return UUID.randomUUID().toString();
  }
}
