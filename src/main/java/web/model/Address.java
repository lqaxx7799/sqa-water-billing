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

	private String city;

	private String country;

	@Column(name="house_number")
	private String houseNumber;

	private String province;

	private String street;

	//bi-directional many-to-one association to Area
	@ManyToOne
	@JoinColumn(name="tbl_area_id")
	private Area tblArea;

	//bi-directional many-to-many association to Customer
	@ManyToMany(mappedBy="tblAddresses")
	private List<Customer> tblCustomers;

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

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Area getTblArea() {
		return this.tblArea;
	}

	public void setTblArea(Area tblArea) {
		this.tblArea = tblArea;
	}

	public List<Customer> getTblCustomers() {
		return this.tblCustomers;
	}

	public void setTblCustomers(List<Customer> tblCustomers) {
		this.tblCustomers = tblCustomers;
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