package web.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.District;
import web.model.Province;

public interface DistrictRepository extends JpaRepository<District, Integer> {
	public List<District> findByTblProvince(Province tblProvince);
}
