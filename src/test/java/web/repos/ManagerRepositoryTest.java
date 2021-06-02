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
import web.model.Manager;
import web.model.Ward;
import web.util.CommonUtils;

@DataJpaTest
public class ManagerRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    ManagerRepository repository;

	@Test
    public void find_manager_by_id() {
		Manager manager = new Manager();
		manager.setFirstName("Test");
         
        repository.save(manager);
         
        Assertions.assertNotNull(manager.getId());
    }
	
	@Test
    public void find_all() {
		Manager manager1 = new Manager();
		manager1.setFirstName("Anna");
        
		Manager manager2 = new Manager();
		manager2.setFirstName("Bella");
         
        entityManager.persistAndFlush(manager1);
        entityManager.persistAndFlush(manager2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
}
