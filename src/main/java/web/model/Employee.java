package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_employee database table.
 * 
 */
@Entity
@Table(name="tbl_employee")
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="first_name")
	private String firstName;

	@Column(name="is_working")
	private byte isWorking;

	@Column(name="last_name")
	private String lastName;

	@Column(name="phone_number")
	private String phoneNumber;

	private float salary;

	//bi-directional many-to-many association to Area
	@ManyToMany(mappedBy="tblEmployees")
	private List<Area> tblAreas;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="tbl_account_id")
	private Account tblAccount;

	//bi-directional many-to-one association to WaterBill
	@OneToMany(mappedBy="tblEmployee")
	private List<WaterBill> tblWaterBills;

	//bi-directional many-to-one association to WaterMeterReading
	@OneToMany(mappedBy="tblEmployee")
	private List<WaterMeterReading> tblWaterMeterReadings;

	public Employee() {
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

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public byte getIsWorking() {
		return this.isWorking;
	}

	public void setIsWorking(byte isWorking) {
		this.isWorking = isWorking;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public float getSalary() {
		return this.salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public List<Area> getTblAreas() {
		return this.tblAreas;
	}

	public void setTblAreas(List<Area> tblAreas) {
		this.tblAreas = tblAreas;
	}

	public Account getTblAccount() {
		return this.tblAccount;
	}

	public void setTblAccount(Account tblAccount) {
		this.tblAccount = tblAccount;
	}

	public List<WaterBill> getTblWaterBills() {
		return this.tblWaterBills;
	}

	public void setTblWaterBills(List<WaterBill> tblWaterBills) {
		this.tblWaterBills = tblWaterBills;
	}

	public WaterBill addTblWaterBill(WaterBill tblWaterBill) {
		getTblWaterBills().add(tblWaterBill);
		tblWaterBill.setTblEmployee(this);

		return tblWaterBill;
	}

	public WaterBill removeTblWaterBill(WaterBill tblWaterBill) {
		getTblWaterBills().remove(tblWaterBill);
		tblWaterBill.setTblEmployee(null);

		return tblWaterBill;
	}

	public List<WaterMeterReading> getTblWaterMeterReadings() {
		return this.tblWaterMeterReadings;
	}

	public void setTblWaterMeterReadings(List<WaterMeterReading> tblWaterMeterReadings) {
		this.tblWaterMeterReadings = tblWaterMeterReadings;
	}

	public WaterMeterReading addTblWaterMeterReading(WaterMeterReading tblWaterMeterReading) {
		getTblWaterMeterReadings().add(tblWaterMeterReading);
		tblWaterMeterReading.setTblEmployee(this);

		return tblWaterMeterReading;
	}

	public WaterMeterReading removeTblWaterMeterReading(WaterMeterReading tblWaterMeterReading) {
		getTblWaterMeterReadings().remove(tblWaterMeterReading);
		tblWaterMeterReading.setTblEmployee(null);

		return tblWaterMeterReading;
	}

}