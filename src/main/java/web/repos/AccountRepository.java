package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query("SELECT a FROM Account a WHERE a.email = :email")
	public Account findOneByEmail(@Param("email") String email);
	
	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.email = :email AND a.password = :password")
	public boolean existByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
