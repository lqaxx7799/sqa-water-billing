package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
