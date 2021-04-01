package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_area database table.
 * 
 */
@Entity
@Table(name="tbl_area")
@NamedQuery(name="Area.findAll", query="SELECT a FROM Area a")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String city;

	private String country;

	private String province;

	//bi-directional many-to-one association to Address
	@OneToMany(mappedBy="tblArea")
	private List<Address> tblAddresses;

	//bi-directional many-to-many association to Employee
	@ManyToMany
	@JoinTable(
		name="tbl_assigned_area"
		, joinColumns={
			@JoinColumn(name="tbl_area_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="tbl_employee_id")
			}
		)
	private List<Employee> tblEmployees;

	public Area() {
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

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public List<Address> getTblAddresses() {
		return this.tblAddresses;
	}

	public void setTblAddresses(List<Address> tblAddresses) {
		this.tblAddresses = tblAddresses;
	}

	public Address addTblAddress(Address tblAddress) {
		getTblAddresses().add(tblAddress);
		tblAddress.setTblArea(this);

		return tblAddress;
	}

	public Address removeTblAddress(Address tblAddress) {
		getTblAddresses().remove(tblAddress);
		tblAddress.setTblArea(null);

		return tblAddress;
	}

	public List<Employee> getTblEmployees() {
		return this.tblEmployees;
	}

	public void setTblEmployees(List<Employee> tblEmployees) {
		this.tblEmployees = tblEmployees;
	}

}