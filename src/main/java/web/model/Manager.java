package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_manager database table.
 * 
 */
@Entity
@Table(name="tbl_manager")
@NamedQuery(name="Manager.findAll", query="SELECT m FROM Manager m")
public class Manager implements Serializable {
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

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="tbl_account_id")
	private Account tblAccount;

	public Manager() {
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

	public Account getTblAccount() {
		return this.tblAccount;
	}

	public void setTblAccount(Account tblAccount) {
		this.tblAccount = tblAccount;
	}

}