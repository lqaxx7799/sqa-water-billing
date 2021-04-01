package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
