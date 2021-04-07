package web.repos;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.model.WaterMeterReading;

public interface WaterMeterReadingRepository extends JpaRepository<WaterMeterReading, Integer> {
	@Query("select a from WaterMeterReading a where a.month = :month and a.year = :year and a.tblWaterMeter.tblAddress.tblWard.id = :wardId")
	public List<WaterMeterReading> findByPeriodAndArea(@Param("month") int month, @Param("year") int year, @Param("wardId") int wardId);
	
	@Query("select a from WaterMeterReading a where a.month = :month and a.year = :year and a.tblWaterMeter.id= :waterMeterId")
	public List<WaterMeterReading> _findOneByPeriodAndMeter(@Param("month") int month, @Param("year") int year, @Param("waterMeterId") int waterMeterId, Pageable page);

	default WaterMeterReading findOneByPeriodAndMeter(int month, int year, int waterMeterId){
		List<WaterMeterReading> result = _findOneByPeriodAndMeter(
	        month,
	        year,
	        waterMeterId,
	        PageRequest.of(0,1)
	    );
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	};
}
