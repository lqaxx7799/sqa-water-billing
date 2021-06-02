package web.repos;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import web.model.Address;
import web.model.AddressType;
import web.model.Customer;
import web.model.Pricing;
import web.model.WaterMeter;
import web.model.WaterMeterReading;

@DataJpaTest
public class PricingRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    PricingRepository repository;
	
	@Test
    public void find_pricing_by_id() {
		Pricing pricing = new Pricing();
		pricing.setUnitPriceLevel1(1000);
		pricing.setId(1);
         
        repository.save(pricing);
         
        Assertions.assertNotNull(pricing.getId());
    }
	
	@Test
    public void find_all() {
		Pricing pricing1 = new Pricing();
		pricing1.setUnitPriceLevel1(1000);
		
		Pricing pricing2 = new Pricing();
		pricing2.setUnitPriceLevel1(1200);
         
        entityManager.persistAndFlush(pricing1);
        entityManager.persistAndFlush(pricing2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_one_by_address_type() {
		AddressType addressType = new AddressType();
		
		Pricing pricing1 = new Pricing();
		pricing1.setUnitPriceLevel1(1000);
		pricing1.setTblAddressType(addressType);
		pricing1.setIsApplying(false);
		
		Pricing pricing2 = new Pricing();
		pricing2.setUnitPriceLevel1(1200);
		pricing2.setTblAddressType(addressType);
		pricing2.setIsApplying(true);
        
		entityManager.persistAndFlush(addressType);
        entityManager.persistAndFlush(pricing1);
        entityManager.persistAndFlush(pricing2);
         
        Assertions.assertEquals(1200, repository.findOneByTblAddressType(addressType.getId()).getUnitPriceLevel1());
    }
	
	@Test
    public void find_one_by_address_type_and_period() {
		AddressType addressType = new AddressType();
		
		Pricing pricing1 = new Pricing();
		pricing1.setUnitPriceLevel1(1000);
		pricing1.setTblAddressType(addressType);
		pricing1.setIsApplying(false);
		Calendar calendar11 = Calendar.getInstance();
		calendar11.set(2021, 0, 1);
		pricing1.setAppliedFrom(calendar11.getTime());
		Calendar calendar12 = Calendar.getInstance();
		calendar12.set(2021, 2, 1);
		pricing1.setAppliedTo(calendar12.getTime());
		
		Pricing pricing2 = new Pricing();
		pricing2.setUnitPriceLevel1(1200);
		pricing2.setTblAddressType(addressType);
		pricing2.setIsApplying(true);
		Calendar calendar21 = Calendar.getInstance();
		calendar21.set(2021, 2, 2);
		pricing2.setAppliedFrom(calendar21.getTime());
        
		entityManager.persistAndFlush(addressType);
        entityManager.persistAndFlush(pricing1);
        entityManager.persistAndFlush(pricing2);
        
        Calendar calendar = Calendar.getInstance();
		calendar.set(2021, 5, 1);
         
        Assertions.assertEquals(1200, repository.findOneByTblAddressTypeAndPeriod(addressType.getId(), calendar.getTime()).getUnitPriceLevel1());
    }
}
