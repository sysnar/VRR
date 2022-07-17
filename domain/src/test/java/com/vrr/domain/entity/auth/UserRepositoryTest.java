package com.vrr.domain.entity.auth;

import com.vrr.common.code.auth.ProviderType;
import com.vrr.common.code.auth.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB를 활용해 테스트하고 싶은 때 사용
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void OAUTH2_유저_생성_테스트() {
        // given
        LocalDateTime signUpDate = LocalDateTime.of(2022, 6, 8, 9, 20);
        User user = new User(UUID.randomUUID().toString(), "김상윤", "sysn4r@gmail.com", "Y", "https://sysnar.github.io/images/profile-avatar.png", ProviderType.GOOGLE, RoleType.USER, signUpDate, signUpDate);

        // when
        user = userRepository.save(user);
        User foundUser = userRepository.findById(user.getId()).get();

        // then
        assertThat(foundUser.getEmail()).isEqualTo("sysn4r@gmail.com");
        assertThat(foundUser.getUsername()).isEqualTo("김상윤");
    }
}