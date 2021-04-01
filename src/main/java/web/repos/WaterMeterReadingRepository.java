package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.WaterMeterReading;

public interface WaterMeterReadingRepository extends JpaRepository<WaterMeterReading, Integer> {

}
