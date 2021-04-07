package web.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.model.Address;
import web.model.Ward;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	public List<Address> findByTblWard(Ward ward);
}
