package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.WaterMeter;

public interface WaterMeterRepository extends JpaRepository<WaterMeter, Integer> {

}
