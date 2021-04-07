package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_address_type database table.
 * 
 */
@Entity
@Table(name="tbl_address_type")
@NamedQuery(name="AddressType.findAll", query="SELECT a FROM AddressType a")
public class AddressType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String description;

	private String type;

	//bi-directional many-to-one association to Address
	@OneToMany(mappedBy="tblAddressType")
	private List<Address> tblAddresses;

	//bi-directional many-to-one association to Pricing
	@OneToMany(mappedBy="tblAddressType")
	private List<Pricing> tblPricings;

	public AddressType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Address> getTblAddresses() {
		return this.tblAddresses;
	}

	public void setTblAddresses(List<Address> tblAddresses) {
		this.tblAddresses = tblAddresses;
	}

	public Address addTblAddress(Address tblAddress) {
		getTblAddresses().add(tblAddress);
		tblAddress.setTblAddressType(this);

		return tblAddress;
	}

	public Address removeTblAddress(Address tblAddress) {
		getTblAddresses().remove(tblAddress);
		tblAddress.setTblAddressType(null);

		return tblAddress;
	}

	public List<Pricing> getTblPricings() {
		return this.tblPricings;
	}

	public void setTblPricings(List<Pricing> tblPricings) {
		this.tblPricings = tblPricings;
	}

	public Pricing addTblPricing(Pricing tblPricing) {
		getTblPricings().add(tblPricing);
		tblPricing.setTblAddressType(this);

		return tblPricing;
	}

	public Pricing removeTblPricing(Pricing tblPricing) {
		getTblPricings().remove(tblPricing);
		tblPricing.setTblAddressType(null);

		return tblPricing;
	}

}