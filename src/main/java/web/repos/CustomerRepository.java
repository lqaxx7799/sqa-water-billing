package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.model.Customer;
import web.model.Employee;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	@Query("SELECT e FROM Customer e WHERE e.tblAccount.id = :accountId")
	public Customer findOneByAccountId(@Param("accountId") int accountId);
}
