package ru.mkr.mortgageapplicationservice.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public interface CustomerRepository
    extends JpaRepository<Customer, String> {

  Customer findByFirstNameAndSecondNameAndLastNameAndPassport(String firstName, String secondName,
                                                              String lastName, String passport);
}
