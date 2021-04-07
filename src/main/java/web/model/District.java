package web.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the tbl_district database table.
 * 
 */
@Entity
@Table(name="tbl_district")
@NamedQuery(name="District.findAll", query="SELECT d FROM District d")
public class District implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="district_name")
	private String districtName;

	//bi-directional many-to-one association to Province
	@ManyToOne
	@JoinColumn(name="tbl_province_id")
	@JsonManagedReference
	private Province tblProvince;

	//bi-directional many-to-one association to Ward
	@OneToMany(mappedBy="tblDistrict")
	@JsonBackReference
	private List<Ward> tblWards;

	public District() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Province getTblProvince() {
		return this.tblProvince;
	}

	public void setTblProvince(Province tblProvince) {
		this.tblProvince = tblProvince;
	}

	public List<Ward> getTblWards() {
		return this.tblWards;
	}

	public void setTblWards(List<Ward> tblWards) {
		this.tblWards = tblWards;
	}

	public Ward addTblWard(Ward tblWard) {
		getTblWards().add(tblWard);
		tblWard.setTblDistrict(this);

		return tblWard;
	}

	public Ward removeTblWard(Ward tblWard) {
		getTblWards().remove(tblWard);
		tblWard.setTblDistrict(null);

		return tblWard;
	}

}