package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.District;

public interface DistrictRepository extends JpaRepository<District, Integer> {

}
