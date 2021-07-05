package qna.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.save(SANJIGI);
        userRepository.save(JAVAJIGI);
    }

    @Test
    public void 사용자를_ID_값으로_조회() {
        final Optional<User> user1 = userRepository.findById(JAVAJIGI.getId());
        assertThat(user1.get()).isEqualTo(JAVAJIGI);

        final Optional<User> user2 = userRepository.findById(SANJIGI.getId());
        assertThat(user2.get()).isEqualTo(SANJIGI);
    }

    @Test
    public void 사용자를_유저_ID로_조회() {
        final Optional<User> user1 = userRepository.findByUserId(JAVAJIGI.getUserId());
        assertThat(user1.get()).isEqualTo(JAVAJIGI);

        final Optional<User> user2 = userRepository.findByUserId(SANJIGI.getUserId());
        assertThat(user2.get()).isEqualTo(SANJIGI);
    }

    @Test
    public void 사용자의_유저_ID를_바꿀_수_있다() {
        final User user = new User("userId", "password", "name", "email");
        final User savedUser = userRepository.save(user);
        savedUser.setUserId("JOEL");
        final Optional<User> queriedUser = userRepository.findByUserId("JOEL");
        assertThat(queriedUser.get()).isEqualTo(savedUser);
    }

    @Test
    public void 사용자의_유저_ID를_바꿀_수_있다2() {
        final User user = new User("userId", "password", "name", "email");
        userRepository.save(user);
        user.setUserId("JOEL");
        final Optional<User> queriedUser = userRepository.findByUserId("JOEL");
        assertThat(queriedUser.get()).isEqualTo(user);
    }

    @Test
    public void 사용자를_삭제할_수_있다() {
        List<User> all = userRepository.findAll();
        assertThat(all).hasSize(2);

        userRepository.delete(JAVAJIGI);
        all = userRepository.findAll();
        assertThat(all).hasSize(1);

        userRepository.delete(SANJIGI);
        all = userRepository.findAll();
        assertThat(all).hasSize(0);
    }
}
