package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
