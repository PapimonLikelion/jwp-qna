package qna.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import qna.domain.Answer;
import qna.domain.Question;
import qna.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@DataJpaTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class AnswerRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    private final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    private final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    private final Question Q1 = new Question("title1", "contents1").writeBy(JAVAJIGI);
    private final Question Q2 = new Question("title2", "contents2").writeBy(SANJIGI);

    private final Answer A1 = new Answer(JAVAJIGI, Q1, "Answers Contents1");
    private final Answer A2 = new Answer(SANJIGI, Q1, "Answers Contents2");

    @BeforeEach
    void setUp() {
        userRepository.save(JAVAJIGI);
        userRepository.save(SANJIGI);
        questionRepository.save(Q1);
        questionRepository.save(Q2);
    }

    @Test
    void 답변_저장() {
        answerRepository.save(A1);
        answerRepository.save(A2);
        assertThat(answerRepository.findAll()).hasSize(2);
    }

    @Test
    void 답변_조회() {
        answerRepository.save(A1);

        final Optional<Answer> answer = answerRepository.findByIdAndDeletedFalse(A1.getId());
        assertThat(answer.get()).isEqualTo(A1);
    }

    @Test
    void 답변_조회_후_질문_조회() {
        answerRepository.save(A1);

        final Optional<Answer> answer = answerRepository.findByIdAndDeletedFalse(A1.getId());
        final Question question = answer.get().getQuestion();

        assertThat(question).isEqualTo(A1.getQuestion());
    }

    @Test
    void 답변_조회_후_작성자_조회() {
        answerRepository.save(A1);

        final Optional<Answer> answer = answerRepository.findByIdAndDeletedFalse(A1.getId());
        final User writer = answer.get().getWriter();

        assertThat(writer).isEqualTo(A1.getWriter());
    }

    @Test
    void 답변_수정_시_작성자_수정() {
        answerRepository.save(A1);
        final User writer = new User("writer", "writer", "writer", "writer");
        userRepository.save(writer);

        A1.setWriter(writer);

        final Optional<Answer> answer = answerRepository.findByIdAndDeletedFalse(A1.getId());
        assertThat(answer.get()).isEqualTo(A1);
    }

    @Test
    void 답변_삭제() {
        answerRepository.save(A1);
        answerRepository.save(A2);
        assertThat(answerRepository.findAll()).hasSize(2);

        answerRepository.delete(A1);
        assertThat(answerRepository.findAll()).hasSize(1);

        answerRepository.delete(A2);
        assertThat(answerRepository.findAll()).hasSize(0);
    }
}