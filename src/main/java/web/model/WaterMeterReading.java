package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_water_meter_reading database table.
 * 
 */
@Entity
@Table(name="tbl_water_meter_reading")
@NamedQuery(name="WaterMeterReading.findAll", query="SELECT w FROM WaterMeterReading w")
public class WaterMeterReading implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	private int month;

	@Column(name="reading_value")
	private int readingValue;

	private int year;

	//bi-directional many-to-one association to WaterBill
	@OneToMany(mappedBy="tblWaterMeterReading")
	private List<WaterBill> tblWaterBills;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="handled_employee_id")
	private Employee tblEmployee;

	//bi-directional many-to-one association to WaterMeter
	@ManyToOne
	@JoinColumn(name="tbl_water_meter_id")
	private WaterMeter tblWaterMeter;

	public WaterMeterReading() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getReadingValue() {
		return this.readingValue;
	}

	public void setReadingValue(int readingValue) {
		this.readingValue = readingValue;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<WaterBill> getTblWaterBills() {
		return this.tblWaterBills;
	}

	public void setTblWaterBills(List<WaterBill> tblWaterBills) {
		this.tblWaterBills = tblWaterBills;
	}

	public WaterBill addTblWaterBill(WaterBill tblWaterBill) {
		getTblWaterBills().add(tblWaterBill);
		tblWaterBill.setTblWaterMeterReading(this);

		return tblWaterBill;
	}

	public WaterBill removeTblWaterBill(WaterBill tblWaterBill) {
		getTblWaterBills().remove(tblWaterBill);
		tblWaterBill.setTblWaterMeterReading(null);

		return tblWaterBill;
	}

	public Employee getTblEmployee() {
		return this.tblEmployee;
	}

	public void setTblEmployee(Employee tblEmployee) {
		this.tblEmployee = tblEmployee;
	}

	public WaterMeter getTblWaterMeter() {
		return this.tblWaterMeter;
	}

	public void setTblWaterMeter(WaterMeter tblWaterMeter) {
		this.tblWaterMeter = tblWaterMeter;
	}

}