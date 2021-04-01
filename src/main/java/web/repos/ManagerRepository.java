package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

}
