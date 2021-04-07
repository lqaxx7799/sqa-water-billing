package web.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


/**
 * The persistent class for the tbl_payment database table.
 * 
 */
@Entity
@Table(name="tbl_payment")
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean confirmed;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="payment_code")
	private String paymentCode;

	@Column(name="payment_type")
	private String paymentType;

	//bi-directional many-to-one association to WaterBill
	@ManyToOne
	@JoinColumn(name="tbl_water_bill_id")
	private WaterBill tblWaterBill;

	public Payment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getConfirmed() {
		return this.confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getPaymentCode() {
		return this.paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public WaterBill getTblWaterBill() {
		return this.tblWaterBill;
	}

	public void setTblWaterBill(WaterBill tblWaterBill) {
		this.tblWaterBill = tblWaterBill;
	}

}