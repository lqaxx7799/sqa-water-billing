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
import web.util.CommonUtils;

@DataJpaTest
public class AddressTypeRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    AddressTypeRepository repository;

	@Test
    public void find_addressType_by_id() {
		AddressType address1 = new AddressType();
        address1.setType("Ho gia dinh");
         
        repository.save(address1);
         
        Assertions.assertNotNull(address1.getId());
    }
	
	@Test
    public void find_all() {
        AddressType at1 = new AddressType();
        at1.setType("Ho gia dinh");
        
        AddressType at2 = new AddressType();
        at2.setType("Ho kinh doanh");
         
        entityManager.persistAndFlush(at1);
        entityManager.persistAndFlush(at2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
}
