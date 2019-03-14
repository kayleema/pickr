package pickr;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PostServiceImplIntegrationTest {

    @TestConfiguration
    static class PostServiceImplTestContextConfiguration {
        @Bean
        public PostService postService() {
            return new PostServiceImpl();
        }
    }


    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Before
    public void setUp() {
        Post post = new Post("hello");
        Optional<Post> opPost = Optional.of(post);
        Mockito.when(postRepository.findById(2L)).thenReturn(opPost);
    }

    @Test
    public void whenValidId_thenPostShouldBeFound() {
        Long id = 2L;
        Optional<Post> found = postService.getPostById(id);

        assertThat(found.get().getCaption()).isEqualTo("hello");
    }
}
