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
import web.util.CommonUtils;

@DataJpaTest
public class PaymentRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    PaymentRepository repository;

	@Test
    public void find_payment_by_id() {
		Payment payment1 = new Payment();
		payment1.setOtpCode("045554");
		payment1.setId(1);
         
        repository.save(payment1);
         
        Assertions.assertNotNull(payment1.getId());
    }
	
	@Test
    public void find_all() {
		Payment payment1 = new Payment();
		payment1.setOtpCode("045554");
        
        Payment payment2 = new Payment();
        payment2.setOtpCode("123456");
         
        entityManager.persistAndFlush(payment1);
        entityManager.persistAndFlush(payment2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void find_one_by_water_bill() {
		WaterBill waterBill = new WaterBill();
		

		Payment payment = new Payment();
		payment.setOtpCode("123456");
		payment.setTblWaterBill(waterBill);
         
        entityManager.persistAndFlush(waterBill);
        repository.save(payment);
         
        Assertions.assertNotNull(repository.findOneByWaterBill(waterBill.getId()));
    }
	
}
