package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UsersRepository usersRepository;

    private UserEntity userEntity;

    private UserEntity userEntity2;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("Ramesh");
        userEntity.setLastName("Fadatare");
        userEntity.setEmail("email@email.com");
        userEntity.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(userEntity);

        userEntity2 = new UserEntity();
        userEntity2.setUserId(UUID.randomUUID().toString());
        userEntity2.setFirstName("John");
        userEntity2.setLastName("Doe");
        userEntity2.setEmail("teste@email.com");
        userEntity2.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(userEntity2);
    }

    @Test
    void testFindByEmail_whenGivenCorrectEmail_returnsUserEntity() {
        // Act
        UserEntity foundUserEntity = usersRepository.findByEmail(userEntity.getEmail());

        // Assert
        assertEquals(userEntity.getId(), foundUserEntity.getId(),
                "Different user returned than expected");
    }

    @Test
    void testFindByUserId_whenGivenCorrectUserId_returnsUserEntity() {
        // Act
        UserEntity foundUserEntity = usersRepository.findByUserId(userEntity2.getUserId());

        // Assert
        assertEquals(userEntity2.getId(), foundUserEntity.getId(),
                "Different user returned than expected");
    }

    @Test
    void testFindUsersWithEmailEndsWith_whenGivenEmailDomain_returnsUsersGivenDomian() {
        // Arrange
        UserEntity userEntity3 = new UserEntity();
        userEntity3.setUserId(UUID.randomUUID().toString());
        userEntity3.setFirstName("Carlos");
        userEntity3.setLastName("Fernandes");
        userEntity3.setEmail("carlos@fernandes.com");
        userEntity3.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(userEntity3);

        String emailDomainName = "@fernandes.com";
        List<UserEntity> users = usersRepository.findUsersWithEmailEndingWith(emailDomainName);

        // Assert
        assertEquals(1, users.size(),
                "Different number of users returned than expected");

        assertTrue(users.get(0).getEmail().endsWith(emailDomainName),
                "User returned does not have the expected domain name");
    }

}