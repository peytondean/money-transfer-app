package com.techelevator.repo;

import com.techelevator.tenmo.TenmoApplication;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Authority;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.repo.AccountRepo;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


//@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = TenmoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class AccountRepositoryTest {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void contextLoads(){}

    @Test
    public void testFindById() {
        Set<Authority> authSet = new HashSet<>();
        Authority authority = new Authority("ROLE_USER");
        authSet.add(authority);

        User user = User.builder()
                .username("test")
                .password("password")
                .passwordHash("passwordhashed")
                .activated(true)
                .authorities(authSet)
                .build();


        user = testEntityManager.persistAndFlush(user);

        Account account = new Account();
        account.setBalance(new BigDecimal(500));
        account.setUser(user);


        account = testEntityManager.persistAndFlush(account);

        Optional<Account> foundAccount = accountRepo.findById(account.getId());

        Assertions.assertThat(foundAccount.isPresent()).isTrue();
        Assertions.assertThat(foundAccount.get().getId()).isEqualTo(account.getId());
    }


    @Test
    public void testFindAll() {
        Set<Authority> authSet = new HashSet<>();
        Authority authority = new Authority("ROLE_USER");
        authSet.add(authority);

        User user = User.builder()
                .username("test")
                .password("password")
                .passwordHash("passwordhashed")
                .activated(true)
                .authorities(authSet)
                .build();

        user = testEntityManager.persistAndFlush(user);

        Account account1 = new Account();
        account1.setBalance(new BigDecimal(500));
        account1.setUser(user);
        testEntityManager.persistAndFlush(account1);

        Account account2 = new Account();
        account2.setBalance(new BigDecimal(1000));
        account2.setUser(user);
        testEntityManager.persistAndFlush(account2);

        List<Account> accounts = accountRepo.findAll();

        Assertions.assertThat(accounts.size()).isEqualTo(2);
        Assertions.assertThat(accounts).extracting(Account::getId).containsExactlyInAnyOrder(account1.getId(), account2.getId());
    }

    @Test
    public void testFindByUserId() {
        Set<Authority> authSet = new HashSet<>();
        Authority authority = new Authority("ROLE_USER");
        authSet.add(authority);

        User user = User.builder()
                .username("test")
                .password("password")
                .passwordHash("passwordhashed")
                .activated(true)
                .authorities(authSet)
                .build();

        user = testEntityManager.persistAndFlush(user);

        Account account = new Account();
        account.setBalance(new BigDecimal(500));
        account.setUser(user);

        account = testEntityManager.persistAndFlush(account);

        Optional<Account> foundAccounts = accountRepo.findAccountByUserId(user.getId());

        Assertions.assertThat(foundAccounts).isNotEmpty();
        Assertions.assertThat(foundAccounts.get().getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    public void testSave() {
        Set<Authority> authSet = new HashSet<>();
        Authority authority = new Authority("ROLE_USER");
        authSet.add(authority);

        User user = User.builder()
                .username("test")
                .password("password")
                .passwordHash("passwordhashed")
                .activated(true)
                .authorities(authSet)
                .build();

        user = testEntityManager.persistAndFlush(user);

        Account account = new Account();
        account.setBalance(new BigDecimal(500));
        account.setUser(user);

        Account savedAccount = accountRepo.save(account);

        Assertions.assertThat(savedAccount.getId()).isNotNull();
        Assertions.assertThat(savedAccount.getBalance()).isEqualTo(new BigDecimal(500));
        Assertions.assertThat(savedAccount.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    public void testDelete() {
        Set<Authority> authSet = new HashSet<>();
        Authority authority = new Authority("ROLE_USER");
        authSet.add(authority);

        User user = User.builder()
                .username("test")
                .password("password")
                .passwordHash("passwordhashed")
                .activated(true)
                .authorities(authSet)
                .build();

        user = testEntityManager.persistAndFlush(user);

        Account account = new Account();
        account.setBalance(new BigDecimal(500));
        account.setUser(user);

        account = testEntityManager.persistAndFlush(account);

        accountRepo.delete(account);

        Optional<Account> deletedAccount = accountRepo.findById(account.getId());

        Assertions.assertThat(deletedAccount.isPresent()).isFalse();
    }

    @Test
    public void testUpdate(){
        Set<Authority> authSet = new HashSet<>();
        Authority authority = new Authority("ROLE_USER");
        authSet.add(authority);

        User user = User.builder()
                .username("test")
                .password("password")
                .passwordHash("passwordhashed")
                .activated(true)
                .authorities(authSet)
                .build();

        user = testEntityManager.persistAndFlush(user);

        Account account = new Account();
        account.setBalance(new BigDecimal(500));
        account.setUser(user);

        account = testEntityManager.persistAndFlush(account);

        Account updatedAccountInfo = new Account();
        updatedAccountInfo.setBalance(new BigDecimal(1000));
        updatedAccountInfo.setUser(user);

        accountRepo.save(account);
        Optional<Account> optionalAccount = accountRepo.findById(account.getId());
        assertTrue(optionalAccount.isPresent());

        Account accountToUpdate = optionalAccount.get();
        accountToUpdate.setBalance(updatedAccountInfo.getBalance());
        accountToUpdate.setUser(updatedAccountInfo.getUser());

        Account updatedAccount = accountRepo.save(accountToUpdate);

        Assertions.assertThat(updatedAccount.getBalance()).isEqualTo(updatedAccountInfo.getBalance());
        Assertions.assertThat(updatedAccount.getUser().getId()).isEqualTo(updatedAccountInfo.getUser().getId());
    }

    @Test
    public void testGetBalance(){
        Set<Authority> authSet = new HashSet<>();
        Authority authority = new Authority("ROLE_USER");
        authSet.add(authority);

        User user = User.builder()
                .username("test")
                .password("password")
                .passwordHash("passwordhashed")
                .activated(true)
                .authorities(authSet)
                .build();


        user = testEntityManager.persistAndFlush(user);

        Account account = new Account();
        account.setBalance(new BigDecimal(500));
        account.setUser(user);


        account = testEntityManager.persistAndFlush(account);

        Optional<Account> foundAccount = accountRepo.findById(account.getId());

        Assertions.assertThat(foundAccount.isPresent()).isTrue();
        Assertions.assertThat(foundAccount.get().getBalance()).isEqualTo(account.getBalance());
    }

    @Test
    public void testFindAccountByUserId(){
        Set<Authority> authSet = new HashSet<>();
        Authority authority = new Authority("ROLE_USER");
        authSet.add(authority);

        User user = User.builder()
                .username("test")
                .password("password")
                .passwordHash("passwordhash")
                .activated(true)
                .authorities(authSet)
                .build();

        user = testEntityManager.persistAndFlush(user);

        Account account = new Account();
        account.setBalance(new BigDecimal(500));
        account.setUser(user);

        account = testEntityManager.persistAndFlush(account);

        Optional<Account> foundAccounts = accountRepo.findAccountByUserId(user.getId());

        Assertions.assertThat(foundAccounts).isNotEmpty();
        Assertions.assertThat(foundAccounts.get().getUser().getId()).isEqualTo(user.getId());
    }



}