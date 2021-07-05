# jwp-qna

### 1단계. 엔티티 매핑
- **목표**
    - JPA로 실제 도메인 모델 어떻게 구성하는지 생각하기
    - 객체와 테이블을 어떻게 매핑해야 하는지 알아보기
    - DDL을 유추하여 엔티티 클래스 / 리포지토리 클래스 작성하기
    - `@DataJpaTest`를 사용한 학습 테스트 작성하기

- **질문 사항**
    - 해당 테스트는 대체 왜 실패하는가????
    ```java
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
        public void 사용자의_유저_ID를_바꿀_수_있다23() {
            JAVAJIGI.setUserId("joel");
            final Optional<User> queriedUser = userRepository.findByUserId("joel");
            assertThat(queriedUser.get()).isEqualTo(JAVAJIGI);
        }
    }
    ```

### 2단계. 연관 관계 매핑
- **목표**
    - JPA로 실제 도메인 모델을 구성해보기
        - 객체에서는 참조, 테이블에서는 외래 키
    - 객체 지향적으로 어색하지 않게 도메인을 구성해보자!
