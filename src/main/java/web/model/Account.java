package web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_account database table.
 * 
 */
@Entity
@Table(name="tbl_account")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	private String email;

	private String password;

	private String role;

	//bi-directional many-to-one association to Customer
	@OneToMany(mappedBy="tblAccount")
	private List<Customer> tblCustomers;

	//bi-directional many-to-one association to Employee
	@OneToMany(mappedBy="tblAccount")
	private List<Employee> tblEmployees;

	//bi-directional many-to-one association to Manager
	@OneToMany(mappedBy="tblAccount")
	private List<Manager> tblManagers;

	public Account() {
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Customer> getTblCustomers() {
		return this.tblCustomers;
	}

	public void setTblCustomers(List<Customer> tblCustomers) {
		this.tblCustomers = tblCustomers;
	}

	public Customer addTblCustomer(Customer tblCustomer) {
		getTblCustomers().add(tblCustomer);
		tblCustomer.setTblAccount(this);

		return tblCustomer;
	}

	public Customer removeTblCustomer(Customer tblCustomer) {
		getTblCustomers().remove(tblCustomer);
		tblCustomer.setTblAccount(null);

		return tblCustomer;
	}

	public List<Employee> getTblEmployees() {
		return this.tblEmployees;
	}

	public void setTblEmployees(List<Employee> tblEmployees) {
		this.tblEmployees = tblEmployees;
	}

	public Employee addTblEmployee(Employee tblEmployee) {
		getTblEmployees().add(tblEmployee);
		tblEmployee.setTblAccount(this);

		return tblEmployee;
	}

	public Employee removeTblEmployee(Employee tblEmployee) {
		getTblEmployees().remove(tblEmployee);
		tblEmployee.setTblAccount(null);

		return tblEmployee;
	}

	public List<Manager> getTblManagers() {
		return this.tblManagers;
	}

	public void setTblManagers(List<Manager> tblManagers) {
		this.tblManagers = tblManagers;
	}

	public Manager addTblManager(Manager tblManager) {
		getTblManagers().add(tblManager);
		tblManager.setTblAccount(this);

		return tblManager;
	}

	public Manager removeTblManager(Manager tblManager) {
		getTblManagers().remove(tblManager);
		tblManager.setTblAccount(null);

		return tblManager;
	}

}