package qna.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import qna.domain.Question;
import qna.domain.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@DataJpaTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class QuestionRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    private final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    private final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    private final Question Q1 = new Question("title1", "contents1").writeBy(JAVAJIGI);
    private final Question Q2 = new Question("title2", "contents2").writeBy(SANJIGI);

    @BeforeEach
    public void userSetup() {
        userRepository.save(JAVAJIGI);
        userRepository.save(SANJIGI);
    }

    @Test
    public void 질문_저장() {
        questionRepository.save(Q1);
        questionRepository.save(Q2);

        final List<Question> all = questionRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    public void 질문_조회() {
        questionRepository.save(Q1);

        final Optional<Question> question = questionRepository.findById(Q1.getId());
        assertThat(question.get()).isEqualTo(Q1);
    }

    @Test
    public void 질문_조회_후_작성자_조회() {
        questionRepository.save(Q1);

        final Optional<Question> question = questionRepository.findByIdAndDeletedFalse(Q1.getId());
        final User writer = question.get().getWriter();

        assertThat(writer).isEqualTo(Q1.getWriter());
    }

    @Test
    public void 질문_수정() {
        questionRepository.save(Q1);
        Q1.setTitle("changedTitle");

        final Optional<Question> question = questionRepository.findByIdAndDeletedFalse(Q1.getId());
        assertThat(question.get()).isEqualTo(Q1);
    }

    @Test
    public void 질문_수정_시_작성자_수정() {
        questionRepository.save(Q1);
        final User writer = new User("writer", "writer", "writer", "writer");
        userRepository.save(writer);

        Q1.setWriter(writer);

        final Optional<Question> question = questionRepository.findByIdAndDeletedFalse(Q1.getId());
        assertThat(question.get()).isEqualTo(Q1);
    }

    @Test
    public void 질문_삭제() {
        questionRepository.save(Q1);
        questionRepository.save(Q2);
        assertThat(questionRepository.findAll()).hasSize(2);

        questionRepository.delete(Q1);
        assertThat(questionRepository.findAll()).hasSize(1);

        questionRepository.delete(Q2);
        assertThat(questionRepository.findAll()).hasSize(0);
    }
}
