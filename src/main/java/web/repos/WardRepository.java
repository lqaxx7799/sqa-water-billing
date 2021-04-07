package web.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.District;
import web.model.Province;
import web.model.Ward;

public interface WardRepository extends JpaRepository<Ward, Integer> {

	public List<Ward> findByTblDistrict(District tblDistrict);
}
