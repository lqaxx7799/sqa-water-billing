package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
