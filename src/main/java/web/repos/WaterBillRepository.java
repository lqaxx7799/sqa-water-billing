package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.WaterBill;

public interface WaterBillRepository extends JpaRepository<WaterBill, Integer> {

}
