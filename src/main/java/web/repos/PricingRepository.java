package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.AddressType;
import web.model.Pricing;

public interface PricingRepository extends JpaRepository<Pricing, Integer> {
	public Pricing findOneByTblAddressType(AddressType addressType);
}
