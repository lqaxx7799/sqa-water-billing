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
public class DistrictRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    DistrictRepository repository;

	@Test
    public void find_district_by_id() {
		District district = new District();
		district.setDistrictName("Quan Dong Da");
         
        repository.save(district);
         
        Assertions.assertNotNull(district.getId());
    }
	
	@Test
    public void find_all() {
		District district1 = new District();
		district1.setDistrictName("Anna");
        
		District district2 = new District();
        district2.setDistrictName("Bella");
         
        entityManager.persistAndFlush(district1);
        entityManager.persistAndFlush(district2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_districts_by_province_id() {
		Province province = new Province();
		entityManager.persistAndFlush(province);

		District district = new District();
		district.setDistrictName("Quan Hoan Kiem");
		district.setTblProvince(province);
         
        repository.save(district);
         
        Assertions.assertEquals(1, repository.findByTblProvince(province).size());
    }
}
