package pickr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pickr.PostRestControllerUnitTest.asJsonString;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostRepository repository;

    @Test
    public void givenPosts_whenGetPosts_thenStatus200() throws Exception {

        createTestPost("neko");

        mvc.perform(get("/api/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].caption", is("neko")));
    }

    @Test
    public void whenCreatePost_thenReturnNewPost() throws Exception {
        Post mypost = new Post("shika");

        mvc.perform(
                post("/api/newPost")
                        .content(asJsonString(mypost))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.caption", is(mypost.getCaption())))
                .andExpect(jsonPath("$.id", isA(Integer.class)));

        mvc.perform(get("/api/posts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].caption", is("shika")))
                .andExpect(jsonPath("$[0].id", isA(Integer.class)));
    }

    private void createTestPost(String caption) {
        repository.save(new Post(caption));
    }
}
