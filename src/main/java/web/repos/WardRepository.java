package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.Ward;

public interface WardRepository extends JpaRepository<Ward, Integer> {

}
