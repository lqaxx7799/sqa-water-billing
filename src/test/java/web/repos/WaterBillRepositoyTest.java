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
import web.model.WaterBill;
import web.model.WaterMeter;
import web.model.WaterMeterReading;
import web.util.CommonUtils;

@DataJpaTest
public class WaterBillRepositoyTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    WaterBillRepository repository;

	@Test
    public void find_water_bill_by_id() {
		WaterBill waterBill = new WaterBill();
         
        repository.save(waterBill);
         
        Assertions.assertNotNull(waterBill.getId());
    }
	
	@Test
    public void find_all() {
		WaterBill wb1 = new WaterBill();
        
		WaterBill wb2 = new WaterBill();
         
        entityManager.persistAndFlush(wb1);
        entityManager.persistAndFlush(wb2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_water_bills_by_customer_id() {
		Customer customer = new Customer();
		entityManager.persistAndFlush(customer);
		
		Address address = new Address();
		address.setTblCustomer(customer);
		entityManager.persistAndFlush(address);
		
		WaterMeter waterMeter = new WaterMeter();
		waterMeter.setTblAddress(address);
		entityManager.persistAndFlush(waterMeter);
		
		WaterMeterReading waterMeterReading = new WaterMeterReading();
		waterMeterReading.setTblWaterMeter(waterMeter);
		entityManager.persistAndFlush(waterMeterReading);

		WaterBill waterBill = new WaterBill();
		waterBill.setTblWaterMeterReading(waterMeterReading);
        
        repository.save(waterBill);
         
        Assertions.assertEquals(1, repository.findByCustomerId(customer.getId()).size());
    }
}
