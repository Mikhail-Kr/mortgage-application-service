package ru.mkr.mortgageapplicationservice.controller;

public class CustomerNotFoundException extends RuntimeException{
  CustomerNotFoundException(String id) {
    super("Could not find customer" + " " +id);
  }
}
