package pickr;

import java.util.Optional;

public interface PostService {
    Optional<Post> getPostById(Long id);
}