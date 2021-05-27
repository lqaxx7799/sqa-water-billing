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
import web.model.Ward;
import web.util.CommonUtils;

@DataJpaTest
public class CustomerRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    CustomerRepository repository;

	@Test
    public void find_customer_by_id() {
		Customer customer = new Customer();
		customer.setFirstName("Test");
         
        repository.save(customer);
         
        Assertions.assertNotNull(customer.getId());
    }
	
	@Test
    public void find_all() {
		Customer customer1 = new Customer();
		customer1.setFirstName("Anna");
        
        Customer customer2 = new Customer();
        customer2.setFirstName("Bella");
         
        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_customer_by_account_id() {
		Account account = new Account();
		entityManager.persistAndFlush(account);

		Customer customer1 = new Customer();
		customer1.setFirstName("Anna");
		customer1.setTblAccount(account);
         
        repository.save(customer1);
         
        Assertions.assertEquals(customer1.getFirstName(), repository.findOneByAccountId(account.getId()).getFirstName());
    }
}
