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
import web.model.Payment;
import web.model.Ward;
import web.model.WaterBill;
import web.model.WaterMeter;
import web.model.WaterMeterReading;
import web.util.CommonUtils;

@DataJpaTest
public class WaterMeterReadingRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    WaterMeterReadingRepository repository;

	@Test
    public void find_water_meter_reading_by_id() {
		WaterMeterReading waterMeterReading = new WaterMeterReading();
		waterMeterReading.setReadingValue(10);
		waterMeterReading.setId(1);
         
        repository.save(waterMeterReading);
         
        Assertions.assertNotNull(waterMeterReading.getId());
    }
	
	@Test
    public void find_all() {
		WaterMeterReading waterMeterReading1 = new WaterMeterReading();
		waterMeterReading1.setReadingValue(10);
        
		WaterMeterReading waterMeterReading2 = new WaterMeterReading();
		waterMeterReading2.setReadingValue(20);
         
        entityManager.persistAndFlush(waterMeterReading1);
        entityManager.persistAndFlush(waterMeterReading2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_by_period_and_ward() {
		Ward ward = new Ward();

		Address address = new Address();
		address.setTblWard(ward);
		
		WaterMeter waterMeter = new WaterMeter();
		waterMeter.setTblAddress(address);

		WaterMeterReading waterMeterReading1 = new WaterMeterReading();
		waterMeterReading1.setMonth(6);
		waterMeterReading1.setYear(2021);
		waterMeterReading1.setTblWaterMeter(waterMeter);
		
		entityManager.persistAndFlush(ward);
		entityManager.persistAndFlush(address);
		entityManager.persistAndFlush(waterMeter);
        repository.save(waterMeterReading1);
         
        Assertions.assertEquals(1, repository.findByPeriodAndWard(6, 2021, ward.getId()).size());
    }
	
}
