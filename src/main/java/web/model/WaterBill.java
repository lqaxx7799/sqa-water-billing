package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_water_bill database table.
 * 
 */
@Entity
@Table(name="tbl_water_bill")
@NamedQuery(name="WaterBill.findAll", query="SELECT w FROM WaterBill w")
public class WaterBill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private float amount;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	@Temporal(TemporalType.DATE)
	@Column(name="due_date")
	private Date dueDate;

	@Column(name="is_paid")
	private byte isPaid;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="tblWaterBill")
	private List<Payment> tblPayments;

	//bi-directional many-to-one association to WaterMeterReading
	@ManyToOne
	@JoinColumn(name="tbl_water_meter_reading_id")
	private WaterMeterReading tblWaterMeterReading;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="handled_employee_id")
	private Employee tblEmployee;

	public WaterBill() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmount() {
		return this.amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public byte getIsPaid() {
		return this.isPaid;
	}

	public void setIsPaid(byte isPaid) {
		this.isPaid = isPaid;
	}

	public List<Payment> getTblPayments() {
		return this.tblPayments;
	}

	public void setTblPayments(List<Payment> tblPayments) {
		this.tblPayments = tblPayments;
	}

	public Payment addTblPayment(Payment tblPayment) {
		getTblPayments().add(tblPayment);
		tblPayment.setTblWaterBill(this);

		return tblPayment;
	}

	public Payment removeTblPayment(Payment tblPayment) {
		getTblPayments().remove(tblPayment);
		tblPayment.setTblWaterBill(null);

		return tblPayment;
	}

	public WaterMeterReading getTblWaterMeterReading() {
		return this.tblWaterMeterReading;
	}

	public void setTblWaterMeterReading(WaterMeterReading tblWaterMeterReading) {
		this.tblWaterMeterReading = tblWaterMeterReading;
	}

	public Employee getTblEmployee() {
		return this.tblEmployee;
	}

	public void setTblEmployee(Employee tblEmployee) {
		this.tblEmployee = tblEmployee;
	}

}