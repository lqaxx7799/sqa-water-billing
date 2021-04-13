package web.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.model.AddressType;
import web.model.Payment;
import web.model.Pricing;

public interface PricingRepository extends JpaRepository<Pricing, Integer> {	
	@Query("select a from Pricing a where a.tblAddressType.id = :addressTypeId and a.isApplying = true")
	public List<Pricing> _findOneByTblAddressType(@Param("addressTypeId") int addressTypeId, Pageable page);

	default Pricing findOneByTblAddressType(int addressTypeId){
		List<Pricing> result = _findOneByTblAddressType(
			addressTypeId,
	        PageRequest.of(0,1)
	    );
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	};
	
	@Query("select a from Pricing a where a.tblAddressType.id = :addressTypeId and a.appliedFrom <= :date and (a.appliedTo = null or a.appliedTo > :date)")
	public List<Pricing> _findOneByTblAddressTypeAndPeriod(@Param("addressTypeId") int addressTypeId, @Param("date") Date date, Pageable page);

	default Pricing findOneByTblAddressTypeAndPeriod(int addressTypeId, Date date){
		List<Pricing> result = _findOneByTblAddressTypeAndPeriod(
			addressTypeId,
			date,
	        PageRequest.of(0,1)
	    );
		if (result.isEmpty()) {
			return null;
		}
		return result.get(0);
	};
}
