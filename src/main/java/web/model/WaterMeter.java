package web.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_water_meter database table.
 * 
 */
@Entity
@Table(name="tbl_water_meter")
@NamedQuery(name="WaterMeter.findAll", query="SELECT w FROM WaterMeter w")
public class WaterMeter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="expired_date")
	private Date expiredDate;

	@Temporal(TemporalType.DATE)
	@Column(name="installed_date")
	private Date installedDate;

	@Column(name="is_active")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isActive;

	@Column(name="maximum_reading")
	private int maximumReading;

	//bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name="tbl_address_id")
	private Address tblAddress;

	//bi-directional many-to-one association to WaterMeterReading
	@OneToMany(mappedBy="tblWaterMeter")
	private List<WaterMeterReading> tblWaterMeterReadings;

	public WaterMeter() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Date getInstalledDate() {
		return this.installedDate;
	}

	public void setInstalledDate(Date installedDate) {
		this.installedDate = installedDate;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getMaximumReading() {
		return this.maximumReading;
	}

	public void setMaximumReading(int maximumReading) {
		this.maximumReading = maximumReading;
	}

	public Address getTblAddress() {
		return this.tblAddress;
	}

	public void setTblAddress(Address tblAddress) {
		this.tblAddress = tblAddress;
	}

	public List<WaterMeterReading> getTblWaterMeterReadings() {
		return this.tblWaterMeterReadings;
	}

	public void setTblWaterMeterReadings(List<WaterMeterReading> tblWaterMeterReadings) {
		this.tblWaterMeterReadings = tblWaterMeterReadings;
	}

	public WaterMeterReading addTblWaterMeterReading(WaterMeterReading tblWaterMeterReading) {
		getTblWaterMeterReadings().add(tblWaterMeterReading);
		tblWaterMeterReading.setTblWaterMeter(this);

		return tblWaterMeterReading;
	}

	public WaterMeterReading removeTblWaterMeterReading(WaterMeterReading tblWaterMeterReading) {
		getTblWaterMeterReadings().remove(tblWaterMeterReading);
		tblWaterMeterReading.setTblWaterMeter(null);

		return tblWaterMeterReading;
	}

}