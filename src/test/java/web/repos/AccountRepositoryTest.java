package web.repos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import web.model.Account;
import web.util.CommonUtils;

@DataJpaTest
public class AccountRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired
    AccountRepository repository;
	
	@Test
	public void find_account_by_email() {
		Account account = new Account();
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        account = entityManager.persistAndFlush(account);
        
        assertThat(repository.findOneByEmail("test@gmail.com").getId()).isEqualTo(account.getId());
	}

	@Test
    public void insert_account() {
        Account account = new Account();
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
         
        repository.save(account);
         
        Assertions.assertNotNull(account.getId());
    }
	
	@Test
    public void find_all() {
        Account account1 = new Account();
        account1.setEmail("test1@gmail.com");
        account1.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account1.setRole("EMPLOYEE");
        account1.setCreatedAt(new Date());
        
        Account account2 = new Account();
        account1.setEmail("test2@gmail.com");
        account1.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account1.setRole("EMPLOYEE");
        account1.setCreatedAt(new Date());
         
        entityManager.persistAndFlush(account1);
        entityManager.persistAndFlush(account2);
         
        Assertions.assertEquals(2, repository.findAll().size());
    }
	
	@Test
    public void exist_by_email_and_password_success() {
        Account account = new Account();
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
         
        entityManager.persistAndFlush(account);
         
        Assertions.assertTrue(repository.existByEmailAndPassword("test@gmail.com", CommonUtils.generateSHA1("abcd1234")));
    }
	
	@Test
    public void exist_by_email_and_password_failed_1() {
        Account account = new Account();
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
         
        entityManager.persistAndFlush(account);
         
        Assertions.assertFalse(repository.existByEmailAndPassword("test1@gmail.com", CommonUtils.generateSHA1("abcd1234")));
    }
	
	@Test
    public void exist_by_email_and_password_failed_2() {
        Account account = new Account();
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
         
        entityManager.persistAndFlush(account);
         
        Assertions.assertFalse(repository.existByEmailAndPassword("test@gmail.com", CommonUtils.generateSHA1("abcd1334")));
    }
	
}
