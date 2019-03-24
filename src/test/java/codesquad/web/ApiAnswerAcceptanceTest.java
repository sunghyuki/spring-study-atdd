package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {

    private static final String ANSWER_API = "/answers";
    private static final String QUESTION_API = "/api/questions";
    private Question question;
    private Answer answer;
    private String questionLocation;
    @Before
    public void setUp() {
        question = new Question("Title", "Contents");
        answer = new Answer(defaultUser(), "contents");
        answer.toQuestion(question);
        questionLocation = createResource(QUESTION_API, question, defaultUser());
    }

    @Test
    public void create() {
        String answerLocation = createResource(questionLocation + ANSWER_API, answer, defaultUser());
        Answer createdAnswer = getResource(answerLocation, Answer.class, defaultUser());
        assertThat(createdAnswer).isNotNull();
    }

    @Test
    public void create_failed() {
        ResponseEntity<Void> response = template().postForEntity(questionLocation + ANSWER_API, answer, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void show() {
        Answer createdAnswer = getResource(questionLocation+ANSWER_API, Answer.class, defaultUser());
        assertThat(createdAnswer.getWriter()).isEqualTo(defaultUser());
        assertThat(createdAnswer.getContents()).isEqualTo(answer.getContents());
    }

    @Test
    public void update() {

    }

    @Test
    public void update_failed() {

    }

    @Test
    public void delete() {

    }

    @Test
    public void delete_failed() {

    }
}
