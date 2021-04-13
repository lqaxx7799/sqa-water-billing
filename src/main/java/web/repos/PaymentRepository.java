package web.repos;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.model.Payment;
import web.model.WaterMeterReading;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	@Query("select a from Payment a where a.tblWaterBill.id = :waterBillId")
	public List<Payment> _findOneByWaterBill(@Param("waterBillId") int waterBillId, Pageable page);

	default Payment findOneByWaterBill(int waterBillId){
		List<Payment> result = _findOneByWaterBill(
			waterBillId,
	        PageRequest.of(0,1)
	    );
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	};
}
