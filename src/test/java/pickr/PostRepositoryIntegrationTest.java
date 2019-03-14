package pickr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void whenFindById_thenReturnPost() {
        Post post = new Post("post text");
        entityManager.persist(post);
        entityManager.flush();

        Optional<Post> found = postRepository.findById(post.getId());

        assertThat(found.get().getCaption()).isEqualTo(post.getCaption());
    }

    @Test
    public void savePost() {
        Post post = new Post("post text");
        postRepository.save(post);
        Optional<Post> found = postRepository.findById(post.getId());
        assertThat(found.get().getCaption()).isEqualTo(post.getCaption());
    }
}
