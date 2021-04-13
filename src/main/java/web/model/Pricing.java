package web.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the tbl_pricing database table.
 * 
 */
@Entity
@Table(name="tbl_pricing")
@NamedQuery(name="Pricing.findAll", query="SELECT p FROM Pricing p")
public class Pricing implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="applied_from")
	private Date appliedFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="applied_to")
	private Date appliedTo;

	@Column(name="is_applying")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isApplying;

	@Column(name="unit_price_level_1")
	private float unitPriceLevel1;
	
	@Column(name="unit_price_level_2", nullable = true)
	private float unitPriceLevel2;
	
	@Column(name="unit_price_level_3", nullable = true)
	private float unitPriceLevel3;
	
	@Column(name="unit_price_level_4", nullable = true)
	private float unitPriceLevel4;


	//bi-directional many-to-one association to AddressType
	@ManyToOne
	@JoinColumn(name="tbl_address_type_id")
	private AddressType tblAddressType;

	public Pricing() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getAppliedFrom() {
		return this.appliedFrom;
	}

	public void setAppliedFrom(Date appliedFrom) {
		this.appliedFrom = appliedFrom;
	}

	public Date getAppliedTo() {
		return this.appliedTo;
	}

	public void setAppliedTo(Date appliedTo) {
		this.appliedTo = appliedTo;
	}

	public boolean getIsApplying() {
		return this.isApplying;
	}

	public void setIsApplying(boolean isApplying) {
		this.isApplying = isApplying;
	}

	public AddressType getTblAddressType() {
		return this.tblAddressType;
	}

	public void setTblAddressType(AddressType tblAddressType) {
		this.tblAddressType = tblAddressType;
	}

	public float getUnitPriceLevel1() {
		return unitPriceLevel1;
	}

	public void setUnitPriceLevel1(float unitPriceLevel1) {
		this.unitPriceLevel1 = unitPriceLevel1;
	}

	public float getUnitPriceLevel2() {
		return unitPriceLevel2;
	}

	public void setUnitPriceLevel2(float unitPriceLevel2) {
		this.unitPriceLevel2 = unitPriceLevel2;
	}

	public float getUnitPriceLevel3() {
		return unitPriceLevel3;
	}

	public void setUnitPriceLevel3(float unitPriceLevel3) {
		this.unitPriceLevel3 = unitPriceLevel3;
	}

	public float getUnitPriceLevel4() {
		return unitPriceLevel4;
	}

	public void setUnitPriceLevel4(float unitPriceLevel4) {
		this.unitPriceLevel4 = unitPriceLevel4;
	}

}