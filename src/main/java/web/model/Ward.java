package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tbl_ward database table.
 * 
 */
@Entity
@Table(name="tbl_ward")
@NamedQuery(name="Ward.findAll", query="SELECT w FROM Ward w")
public class Ward implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="ward_name")
	private String wardName;

	//bi-directional many-to-one association to Address
	@OneToMany(mappedBy="tblWard")
	private List<Address> tblAddresses;

	//bi-directional many-to-one association to AssignedArea
//	@OneToMany(mappedBy="tblWard")
//	private List<AssignedArea> tblAssignedAreas;

	//bi-directional many-to-many association to Employee
	@ManyToMany(mappedBy="tblWards")
	private List<Employee> tblEmployees;

	//bi-directional many-to-one association to District
	@ManyToOne
	@JoinColumn(name="tbl_district_id")
	private District tblDistrict;

	public Ward() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWardName() {
		return this.wardName;
	}

	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	public List<Address> getTblAddresses() {
		return this.tblAddresses;
	}

	public void setTblAddresses(List<Address> tblAddresses) {
		this.tblAddresses = tblAddresses;
	}

	public Address addTblAddress(Address tblAddress) {
		getTblAddresses().add(tblAddress);
		tblAddress.setTblWard(this);

		return tblAddress;
	}

	public Address removeTblAddress(Address tblAddress) {
		getTblAddresses().remove(tblAddress);
		tblAddress.setTblWard(null);

		return tblAddress;
	}

//	public List<AssignedArea> getTblAssignedAreas() {
//		return this.tblAssignedAreas;
//	}
//
//	public void setTblAssignedAreas(List<AssignedArea> tblAssignedAreas) {
//		this.tblAssignedAreas = tblAssignedAreas;
//	}
//
//	public AssignedArea addTblAssignedArea(AssignedArea tblAssignedArea) {
//		getTblAssignedAreas().add(tblAssignedArea);
//		tblAssignedArea.setTblWard(this);
//
//		return tblAssignedArea;
//	}
//
//	public AssignedArea removeTblAssignedArea(AssignedArea tblAssignedArea) {
//		getTblAssignedAreas().remove(tblAssignedArea);
//		tblAssignedArea.setTblWard(null);
//
//		return tblAssignedArea;
//	}

	public List<Employee> getTblEmployees() {
		return this.tblEmployees;
	}

	public void setTblEmployees(List<Employee> tblEmployees) {
		this.tblEmployees = tblEmployees;
	}

	public District getTblDistrict() {
		return this.tblDistrict;
	}

	public void setTblDistrict(District tblDistrict) {
		this.tblDistrict = tblDistrict;
	}

}