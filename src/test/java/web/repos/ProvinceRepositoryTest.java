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
public class ProvinceRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    ProvinceRepository repository;

	@Test
    public void find_province_by_id() {
		Province province = new Province();
		province.setProvinceName("TP Hà Nội");
         
        repository.save(province);
         
        Assertions.assertNotNull(province.getId());
    }
	
	@Test
    public void find_all() {
		Province province1 = new Province();
		province1.setProvinceName("TP Hà Nội");
        
		Province province2 = new Province();
		province2.setProvinceName("Tỉnh Nam Định");
         
        entityManager.persistAndFlush(province1);
        entityManager.persistAndFlush(province2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
}
