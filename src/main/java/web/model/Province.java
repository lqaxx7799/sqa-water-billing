package web.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the tbl_province database table.
 * 
 */
@Entity
@Table(name="tbl_province")
@NamedQuery(name="Province.findAll", query="SELECT p FROM Province p")
public class Province implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="province_name")
	private String provinceName;

	//bi-directional many-to-one association to District
	@OneToMany(mappedBy="tblProvince")
	@JsonBackReference
	private List<District> tblDistricts;

	public Province() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public List<District> getTblDistricts() {
		return this.tblDistricts;
	}

	public void setTblDistricts(List<District> tblDistricts) {
		this.tblDistricts = tblDistricts;
	}

	public District addTblDistrict(District tblDistrict) {
		getTblDistricts().add(tblDistrict);
		tblDistrict.setTblProvince(this);

		return tblDistrict;
	}

	public District removeTblDistrict(District tblDistrict) {
		getTblDistricts().remove(tblDistrict);
		tblDistrict.setTblProvince(null);

		return tblDistrict;
	}

}