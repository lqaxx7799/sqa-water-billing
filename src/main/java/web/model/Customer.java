package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_customer database table.
 * 
 */
@Entity
@Table(name="tbl_customer")
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	@Temporal(TemporalType.DATE)
	@Column(name="date_of_birth")
	private Date dateOfBirth;

	@Column(name="first_name")
	private String firstName;

	private String gender;

	@Column(name="id_number")
	private String idNumber;

	@Column(name="is_verified")
	private byte isVerified;

	@Column(name="last_name")
	private String lastName;

	@Column(name="phone_number")
	private String phoneNumber;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="tbl_account_id")
	private Account tblAccount;

	//bi-directional many-to-many association to Address
	@ManyToMany
	@JoinTable(
		name="tbl_customer_address"
		, joinColumns={
			@JoinColumn(name="tbl_customer_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="tbl_address_id")
			}
		)
	private List<Address> tblAddresses;

	public Customer() {
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

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public byte getIsVerified() {
		return this.isVerified;
	}

	public void setIsVerified(byte isVerified) {
		this.isVerified = isVerified;
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

	public List<Address> getTblAddresses() {
		return this.tblAddresses;
	}

	public void setTblAddresses(List<Address> tblAddresses) {
		this.tblAddresses = tblAddresses;
	}

}