package ru.mkr.mortgageapplicationservice.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Customer {
  @Id
  @Column(name = "id", nullable = false)
  private String id;
  @Column(name = "first_Name")
  private String firstName;
  @Column(name = "second_Name")
  private String secondName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "passport")
  private String passport;
  @Column(name = "birth_Date")
  private LocalDate birthDate;
  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private Gender gender;
  @Column(name = "salary")
  private int salary;
  @Column(name = "credit_Amount")
  private int creditAmount;
  @Column(name = "duration_In_Months")
  private int durationInMonths;
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Column(name = "monthly_payment")
  private BigDecimal monthlyPayment;

  public Customer() {
  }

  public Customer(String id, String firstName, String secondName, String lastName,
                  String passport, LocalDate birthDate, Gender gender,
                  int salary, int creditAmount, int durationInMonth, Status status) {
    this.id = id;
    this.firstName = firstName;
    this.secondName = secondName;
    this.lastName = lastName;
    this.passport = passport;
    this.birthDate = birthDate;
    this.gender = gender;
    this.salary = salary;
    this.creditAmount = creditAmount;
    this.durationInMonths = durationInMonth;
    this.status = status;
  }

  public Customer(String id, String firstName, String secondName, String lastName,
                  String passport, LocalDate birthDate, Gender gender,
                  int salary, int creditAmount, int durationInMonth) {
    this.id = id;
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

  public Customer(String firstName, String secondName, String lastName,
                  String passport, LocalDate birthDate, Gender gender,
                  int salary, int creditAmount, int durationInMonth) {
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

  public Status getStatus() {
     if(this.status != null) {
       return status;
     } else {
       return Status.PROCESSING;
     }
  }

  public boolean poleNoEmpty() {
    return this.firstName != null &&
            this.secondName != null &&
            this.lastName != null &&
            this.passport != null &&
            this.birthDate != null &&
            this.gender != null;
  }

  public boolean poleNoZero() {
    return this.salary != 0 &&
        this.creditAmount != 0 &&
        this.durationInMonths != 0;
  }
}