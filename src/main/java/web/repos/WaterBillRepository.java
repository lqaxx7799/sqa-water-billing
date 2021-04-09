package web.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.WaterBill;
import web.model.WaterMeterReading;

public interface WaterBillRepository extends JpaRepository<WaterBill, Integer> {
	public WaterBill findOneByTblWaterMeterReading(WaterMeterReading reading);
}
