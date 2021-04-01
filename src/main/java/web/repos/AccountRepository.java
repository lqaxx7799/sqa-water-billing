package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import web.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
