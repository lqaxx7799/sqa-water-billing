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

	@Column(name="unit_price")
	private float unitPrice;

	@Column(name="usage_range_from")
	private float usageRangeFrom;

	@Column(name="usage_range_to")
	private float usageRangeTo;

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

	public float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public float getUsageRangeFrom() {
		return this.usageRangeFrom;
	}

	public void setUsageRangeFrom(float usageRangeFrom) {
		this.usageRangeFrom = usageRangeFrom;
	}

	public float getUsageRangeTo() {
		return this.usageRangeTo;
	}

	public void setUsageRangeTo(float usageRangeTo) {
		this.usageRangeTo = usageRangeTo;
	}

	public AddressType getTblAddressType() {
		return this.tblAddressType;
	}

	public void setTblAddressType(AddressType tblAddressType) {
		this.tblAddressType = tblAddressType;
	}

}