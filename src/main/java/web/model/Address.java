package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_address database table.
 * 
 */
@Entity
@Table(name="tbl_address")
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="house_number")
	private String houseNumber;

	private String street;

	//bi-directional many-to-one association to AddressType
	@ManyToOne
	@JoinColumn(name="tbl_address_type_id")
	private AddressType tblAddressType;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="tbl_customer_id")
	private Customer tblCustomer;

	//bi-directional many-to-one association to Ward
	@ManyToOne
	@JoinColumn(name="tbl_ward_id")
	private Ward tblWard;

	//bi-directional many-to-one association to WaterMeter
	@OneToMany(mappedBy="tblAddress")
	private List<WaterMeter> tblWaterMeters;

	public Address() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public AddressType getTblAddressType() {
		return this.tblAddressType;
	}

	public void setTblAddressType(AddressType tblAddressType) {
		this.tblAddressType = tblAddressType;
	}

	public Customer getTblCustomer() {
		return this.tblCustomer;
	}

	public void setTblCustomer(Customer tblCustomer) {
		this.tblCustomer = tblCustomer;
	}

	public Ward getTblWard() {
		return this.tblWard;
	}

	public void setTblWard(Ward tblWard) {
		this.tblWard = tblWard;
	}

	public List<WaterMeter> getTblWaterMeters() {
		return this.tblWaterMeters;
	}

	public void setTblWaterMeters(List<WaterMeter> tblWaterMeters) {
		this.tblWaterMeters = tblWaterMeters;
	}

	public WaterMeter addTblWaterMeter(WaterMeter tblWaterMeter) {
		getTblWaterMeters().add(tblWaterMeter);
		tblWaterMeter.setTblAddress(this);

		return tblWaterMeter;
	}

	public WaterMeter removeTblWaterMeter(WaterMeter tblWaterMeter) {
		getTblWaterMeters().remove(tblWaterMeter);
		tblWaterMeter.setTblAddress(null);

		return tblWaterMeter;
	}

}