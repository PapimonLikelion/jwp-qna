package qna.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import qna.domain.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@DataJpaTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    private final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    private final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @BeforeEach
    public void setUp() {
        userRepository.save(JAVAJIGI);
        userRepository.save(SANJIGI);
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
