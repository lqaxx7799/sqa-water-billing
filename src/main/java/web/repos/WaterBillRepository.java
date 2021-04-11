package web.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.model.Customer;
import web.model.WaterBill;
import web.model.WaterMeterReading;

public interface WaterBillRepository extends JpaRepository<WaterBill, Integer> {
	public WaterBill findOneByTblWaterMeterReading(WaterMeterReading reading);
	
	@Query("select a from WaterBill a where a.tblWaterMeterReading.tblWaterMeter.tblAddress.tblCustomer.id = :customerId")
	public List<WaterBill> findByCustomerId(@Param("customerId") int customerId);
}
