package web.repos;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import web.model.Address;
import web.model.Customer;
import web.model.WaterMeter;
import web.model.WaterMeterReading;

@DataJpaTest
public class WaterMeterRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    WaterMeterRepository repository;
	
	@Test
    public void find_water_meter_by_id() {
		WaterMeter waterMeter = new WaterMeter();
		waterMeter.setMaximumReading(1000);
		waterMeter.setId(1);
         
        repository.save(waterMeter);
         
        Assertions.assertNotNull(waterMeter.getId());
    }
	
	@Test
    public void find_all() {
		WaterMeter waterMeter1 = new WaterMeter();
		waterMeter1.setMaximumReading(1000);
		
		WaterMeter waterMeter2 = new WaterMeter();
		waterMeter2.setMaximumReading(1200);
         
        entityManager.persistAndFlush(waterMeter1);
        entityManager.persistAndFlush(waterMeter2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_last_by_customer() {
		Customer customer = new Customer();
		
		Address address = new Address();
		address.setTblCustomer(customer);
		
		WaterMeter waterMeter1 = new WaterMeter();
		waterMeter1.setMaximumReading(1000);
		waterMeter1.setTblAddress(address);
		Calendar calendar11 = Calendar.getInstance();
		calendar11.set(2021, 0, 1);
		waterMeter1.setInstalledDate(calendar11.getTime());
		Calendar calendar12 = Calendar.getInstance();
		calendar12.set(2021, 2, 1);
		waterMeter1.setExpiredDate(calendar12.getTime());
		
		WaterMeter waterMeter2 = new WaterMeter();
		waterMeter2.setMaximumReading(1200);
		waterMeter2.setTblAddress(address);
		Calendar calendar21 = Calendar.getInstance();
		calendar21.set(2021, 2, 2);
		waterMeter1.setInstalledDate(calendar21.getTime());
        
		entityManager.persistAndFlush(customer);
		entityManager.persistAndFlush(address);
        entityManager.persistAndFlush(waterMeter1);
        entityManager.persistAndFlush(waterMeter2);
         
        Assertions.assertEquals(1200, repository.findLastByCustomer(customer.getId()).getMaximumReading());
    }
}
