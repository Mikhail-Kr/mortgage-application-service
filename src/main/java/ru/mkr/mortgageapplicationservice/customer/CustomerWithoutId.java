package ru.mkr.mortgageapplicationservice.customer;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.UUID;

public class CustomerWithoutId {

  private String firstName;
  private String secondName;
  private String lastName;
  private String passport;
  private LocalDate birthDate;
  @Enumerated(EnumType.STRING)
  private Customer.Gender gender;
  private int salary;
  private int creditAmount;
  private int durationInMonth;
  String status;

  public CustomerWithoutId(String firstName, String secondName, String lastName,
                           String passport, LocalDate birthDate, Customer.Gender gender,
                           int salary, int creditAmount, int durationInMonth) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.lastName = lastName;
    this.passport = passport;
    this.birthDate = birthDate;
    this.gender = gender;
    this.salary = salary;
    this.creditAmount = creditAmount;
    this.durationInMonth = durationInMonth;
  }


  public Customer getCustomer(CustomerWithoutId customer) {
    return new Customer(generateId(), customer.getFirstName(), customer.getSecondName(), customer.getLastName(), customer.getPassport(),
        customer.getBirthDate(), customer.getGender(), customer.getSalary(), customer.getCreditAmount(), customer.getDurationInMonth(), customer.getStatus());
  }

  private String generateId() {
    return UUID.randomUUID().toString();
  }


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassport() {
    return passport;
  }

  public void setPassport(String passport) {
    this.passport = passport;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public Customer.Gender getGender() {
    return gender;
  }

  public void setGender(Customer.Gender gender) {
    this.gender = gender;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public int getCreditAmount() {
    return creditAmount;
  }

  public void setCreditAmount(int creditAmount) {
    this.creditAmount = creditAmount;
  }

  public int getDurationInMonth() {
    return durationInMonth;
  }

  public void setDurationInMonth(int durationInMonth) {
    this.durationInMonth = durationInMonth;
  }

  public String getStatus() {
    if(status != null) {
      return status;
    } else {
      return "PROCESSING";
    }
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public enum Gender {
    MALE,
    FEMALE
  }
}
