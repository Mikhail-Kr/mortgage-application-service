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

  @Transactional
  @Modifying
  @Query(value = "update customer c set c.status = :#{#status} WHERE c.id = :id", nativeQuery = true)
  void setCustomerStatus(@Param("id") String id, @Param("status") Status status);

  @Transactional
  @Modifying
  @Query(value = "update customer c set c.monthly_Payment = :#{#monthly_Payment} WHERE c.id = :id", nativeQuery = true)
  void setCustomerMonthlyPayment(@Param("id") String id, @Param("monthly_Payment") BigDecimal monthly_Payment);
}
