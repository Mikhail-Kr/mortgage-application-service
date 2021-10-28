package ru.mkr.mortgageapplicationservice.customer;

import lombok.Data;

import javax.persistence.*;
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
  @Column(name = "duration_In_Month")
  private int durationInMonth;
  @Column(name = "status")
  private String status;

  public Customer() {
  }

  public Customer(String id, String firstName, String secondName, String lastName,
                  String passport, LocalDate birthDate, Gender gender,
                  int salary, int creditAmount, int durationInMonth, String status) {
    this.id = id;
    this.firstName = firstName;
    this.secondName = secondName;
    this.lastName = lastName;
    this.passport = passport;
    this.birthDate = birthDate;
    this.gender = gender;
    this.salary = salary;
    this.creditAmount = creditAmount;
    this.durationInMonth = durationInMonth;
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
    this.durationInMonth = durationInMonth;
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
    this.durationInMonth = durationInMonth;
  }

  public String getStatus() {
    if(status != null) {
      return status;
    } else {
      return "PROCESSING";
    }
  }

  public enum Gender {
    MALE,
    FEMALE
  }
}