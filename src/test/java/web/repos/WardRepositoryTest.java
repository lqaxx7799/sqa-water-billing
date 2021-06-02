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
import web.model.District;
import web.model.Province;
import web.model.Ward;
import web.util.CommonUtils;

@DataJpaTest
public class WardRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    WardRepository repository;

	@Test
    public void find_ward_by_id() {
		Ward ward = new Ward();
		ward.setWardName("Phuong Nam Dong");
         
        repository.save(ward);
         
        Assertions.assertNotNull(ward.getId());
    }
	
	@Test
    public void find_all() {
		Ward ward1 = new Ward();
		ward1.setWardName("Phuong Nam Dong");
        
		Ward ward2 = new Ward();
		ward2.setWardName("Phuong Thanh Cong");
         
        entityManager.persistAndFlush(ward1);
        entityManager.persistAndFlush(ward2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_wards_by_district_id() {
		District district = new District();
		entityManager.persistAndFlush(district);

		Ward ward = new Ward();
		ward.setWardName("Phuong Nam Dong");
		ward.setTblDistrict(district);
         
        repository.save(ward);
         
        Assertions.assertEquals(1, repository.findByTblDistrict(district).size());
    }
}
