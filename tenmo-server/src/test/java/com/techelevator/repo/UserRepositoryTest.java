package com.techelevator.repo;

import com.techelevator.tenmo.TenmoApplication;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Authority;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.repo.AccountRepo;
import com.techelevator.tenmo.repo.UserRepo;
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

public class UserRepositoryTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindUserById() {
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

        Optional<User> userFound = userRepo.findById(user.getId());


        Assertions.assertThat(userFound.isPresent()).isTrue();
        Assertions.assertThat(userFound.get().getId()).isEqualTo(user.getId());
    }
/*
    List<User> findAllUsers();

    User saveUser();
    User updateUser();
    void deleteUserById();

    User findUserByUsername();

 */

}