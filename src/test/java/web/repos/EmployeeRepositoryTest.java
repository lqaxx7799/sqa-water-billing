package web.repos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import web.model.Account;
import web.model.Address;
import web.model.AddressType;
import web.model.Customer;
import web.model.Employee;
import web.model.Ward;
import web.util.CommonUtils;

@DataJpaTest
public class EmployeeRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    EmployeeRepository repository;

	@Test
    public void find_employee_by_id() {
		Employee employee = new Employee();
		employee.setFirstName("Test");
         
        repository.save(employee);
         
        Assertions.assertNotNull(employee.getId());
    }
	
	@Test
    public void find_all() {
		Employee employee1 = new Employee();
		employee1.setFirstName("Anna");
        
		Employee employee2 = new Employee();
        employee2.setFirstName("Bella");
         
        entityManager.persistAndFlush(employee1);
        entityManager.persistAndFlush(employee2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_employee_by_account_id() {
		Account account = new Account();
		entityManager.persistAndFlush(account);

		Employee employee = new Employee();
		employee.setFirstName("Anna");
		employee.setTblAccount(account);
         
        repository.save(employee);
         
        Assertions.assertEquals(employee.getFirstName(), repository.findOneByAccountId(account.getId()).getFirstName());
    }
}
