package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @MockBean
    UserRepository userRepository;

    @Test
    void testFindByUsername(){
        User user = new User();
        user.setUsername("username");
        when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
        Optional<User> result = userRepository.findByUsername("username");
        assertEquals(result.get().getUsername(), user.getUsername());
        verify(this.userRepository).findByUsername("username");
    }

}
