package pickr;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> getPostById(Long id);

    List<Post> getAllPosts();

    Post createPost(Post mypost);
}