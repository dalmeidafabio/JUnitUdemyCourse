package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.UUID;

@DataJpaTest
public class UserEntityIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("Ramesh");
        userEntity.setLastName("Fadatare");
        userEntity.setEmail("email@email.com");
        userEntity.setEncryptedPassword("12345678");
    }

    @Test
    @DisplayName("User successfully persisted in database")
    public void testUerEntity_whenValidUserDetailsProvided_souldReturnStoredUserDetails() {
        // Arrange

        // Act
        UserEntity storedUserEntity = testEntityManager.persistAndFlush(userEntity);

        // Assert
        Assertions.assertTrue(storedUserEntity.getId() > 0);
        Assertions.assertEquals(userEntity.getUserId(), storedUserEntity.getUserId());
        Assertions.assertEquals(userEntity.getFirstName(), storedUserEntity.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), storedUserEntity.getLastName());
        Assertions.assertEquals(userEntity.getEmail(), storedUserEntity.getEmail());
        Assertions.assertEquals(userEntity.getEncryptedPassword(), storedUserEntity.getEncryptedPassword());
    }

    @Test
    @DisplayName("Validation should not pass when name is too long ")
    void testUserEntity_whenFirstNameIsTooLong_shouldThrowException() {
        // Arrange
        userEntity.setFirstName("This is a very long first name that is more than 50 characters long");

        //Act and Assert
        Assertions.assertThrows(PersistenceException.class, () -> {
            testEntityManager.persistAndFlush(userEntity);
        }, "Was expecting a PersistenceException to be thrown due to data truncation");
    }

    @Test
    @DisplayName("User unique value validation")
    void testUserEntity_whenExistingUserIdProvided_shouldThrowException() {
        // Arrange
        String userId = UUID.randomUUID().toString();

        UserEntity userEntityWithSameID = new UserEntity();
        userEntityWithSameID.setUserId(userId);
        userEntityWithSameID.setFirstName(userEntity.getFirstName());
        userEntityWithSameID.setLastName(userEntity.getLastName());
        userEntityWithSameID.setEmail(userEntity.getEmail());
        userEntityWithSameID.setEncryptedPassword(userEntity.getEncryptedPassword());
        testEntityManager.persistAndFlush(userEntityWithSameID);

        userEntity.setUserId(userId);

        //Act and Assert
        Assertions.assertThrows(PersistenceException.class, () -> {
            testEntityManager.persistAndFlush(userEntity);
        }, "Was expecting a PersistenceException to be thrown due to unique value violation");
    }

}