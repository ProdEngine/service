package ro.unibuc.hello.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.data.News;
import ro.unibuc.hello.data.NewsRepository;

@SpringBootTest
public class NewsServiceTestIT {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsService newsService;

    News news;
    News updatedNews;
    String id;

    @BeforeEach
    void setUp(){
        id = "622b18512929603cd1c84499";
        news = new News("Latest News", "Latest Description", true);
        news.setId(id);
        updatedNews = new News("Updated News", "Updated Description", false);
        updatedNews.setId("6239be5a0e4cd41dc3d9895b");

    }

    @Test
    void createNews(){
        ResponseEntity<News> result = newsService.createNews(news);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }

   // @Test
   //void updateNews(){
   //     ResponseEntity<News> result1 = newsService.updateNews("623863f74fc7bb197416fd86", updatedNews);
   //     System.out.println(result1.getStatusCode());
   //     Assertions.assertEquals(result1.getStatusCode(), HttpStatus.OK);
   // }

    @Test
    void deleteNews(){
        newsRepository.save(news);
        ResponseEntity<HttpStatus> result = newsService.deleteNews(news.getId());
        Assertions.assertEquals(result, new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
