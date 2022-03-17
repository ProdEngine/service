package ro.unibuc.hello.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class NewsTest {
    String id;
    String title;
    String description;
    boolean published;
    News news1;
    News news2;
    String newsString;
    int hash;
    @BeforeEach
    void setUp() {
        news1 = new News();
        id = "622b18512929603cd1c844ca";
        title = "News";
        description = "Description";
        published = true;
        newsString = "News [id=" + id + ", title=" + title + ", description=" + description + ", published=" + published + "]";
        hash = Objects.hash(id, title, description, published);
        news2 = new News(title, description, published);
        news2.setId(id);

        news1.setId(id);
        news1.setTitle(title);
        news1.setDescription(description);
        news1.setPublished(published);

    }

    @Test
    void getId() {
        assertEquals(id, news1.getId());
        assertEquals(id, news2.getId());
    }

    @Test
    void getTitle() {
        assertEquals(title, news1.getTitle());
        assertEquals(title, news2.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals(description, news1.getDescription());
        assertEquals(description, news2.getDescription());
    }

    @Test
    void isPublished() {
        assertEquals(published, news1.isPublished());
        assertEquals(published, news2.isPublished());
    }

    @Test
    void testToString() {
        assertEquals(newsString, news1.toString());
    }

    @Test
    void testHashCode() {
        assertEquals(hash, news1.hashCode());
    }



}