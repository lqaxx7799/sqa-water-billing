package web.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.model.AddressType;

public interface AddressTypeRepository extends JpaRepository<AddressType, Integer> {
}
