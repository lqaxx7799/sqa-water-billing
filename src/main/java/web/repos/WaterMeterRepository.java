package web.repos;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.model.WaterMeter;
import web.model.WaterMeterReading;

public interface WaterMeterRepository extends JpaRepository<WaterMeter, Integer> {
	@Query("select a from WaterMeter a where a.tblAddress.tblCustomer.id= :customerId order by a.installedDate")
	public List<WaterMeter> _findLastByCustomer(@Param("customerId") int customerId, Pageable page);

	default WaterMeter findLastByCustomer(int customerId){
		List<WaterMeter> result = _findLastByCustomer(
			customerId,
	        PageRequest.of(0,1)
	    );
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	};
}
