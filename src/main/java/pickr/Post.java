package pickr;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String caption;

    public Post(String caption) {
        this.caption = caption;
    }

    private Post() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !Post.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Post p = (Post) obj;
        return p.getCaption().equals(this.getCaption());
    }
}
