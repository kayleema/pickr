package pickr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostRestController.class)
public class PostRestControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService service;

    @Test
    public void givenPosts_whenGetPosts_thenReturnJsonArray()
            throws Exception {

        Post mypost = new Post("elephant");

        List<Post> allPosts = Arrays.asList(mypost);

        given(service.getAllPosts()).willReturn(allPosts);

        mvc.perform(get("/api/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].caption", is(mypost.getCaption())));
    }

    @Test
    public void givenTwoPosts_whenGetPosts_thenReturnJsonArray()
            throws Exception {

        Post mypost = new Post("elephant");
        Post mypost2 = new Post("dolphin");

        List<Post> allPosts = Arrays.asList(mypost, mypost2);

        given(service.getAllPosts()).willReturn(allPosts);

        mvc.perform(get("/api/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].caption", is(mypost.getCaption())))
                .andExpect(jsonPath("$[1].caption", is(mypost2.getCaption())));
    }

    @Test
    public void whenCreatePost_thenReturnNewPost() throws Exception {
        Post mypost = new Post("inu");

        when(service.createPost(mypost)).thenReturn(mypost);

        mvc.perform(
                post("/api/newPost")
                        .content(asJsonString(mypost))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.caption", is(mypost.getCaption())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
