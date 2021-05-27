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
import web.model.Ward;
import web.util.CommonUtils;

@DataJpaTest
public class AddressRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    AddressRepository repository;

	@Test
    public void find_address_by_id() {
		Address address1 = new Address();
        address1.setStreet("5 Tran Phu");
        address1.setId(1);
         
        repository.save(address1);
         
        Assertions.assertNotNull(address1.getId());
    }
	
	@Test
    public void find_all() {
        Address address1 = new Address();
        address1.setStreet("5 Tran Phu");
        
        Address address2 = new Address();
        address2.setStreet("10 Nguyen Trai");
         
        entityManager.persistAndFlush(address1);
        entityManager.persistAndFlush(address2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_addresses_by_ward_id() {
		Ward ward = new Ward();

		Address address1 = new Address();
        address1.setStreet("5 Tran Phu");
        address1.setTblWard(ward);
         
        entityManager.persistAndFlush(ward);
        repository.save(address1);
         
        Assertions.assertEquals(1, repository.findByTblWard(ward).size());
    }
	
}
