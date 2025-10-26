package CommentService.entities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
/*
Super simple test, for the github actions, this test simply tests that a comment
that hasn't gone though the profanity service is marked as unsure.
 */

class CommentTest {

    @Test
    void testProfanity() {
        Comment comment = new Comment(LocalDateTime.now(),"content", 2 );

        Assertions.assertThat(comment.getProfane() == Profanity.UNSURE);
    }
}