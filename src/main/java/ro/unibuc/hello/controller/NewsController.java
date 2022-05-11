package ro.unibuc.hello.controller;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.News;
import ro.unibuc.hello.services.NewsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// Controller that provides APIs for creating, retrieving, updating, deleting and finding News.


@RestController // is used to define a controller and to indicate that the return value of the methods should be bound to the web response body.
public class NewsController {
    @Autowired
    NewsService newsService;

    // ResponseEntity represents the whole HTTP response: status code, headers, and body.

    @Timed(value = "news.getAllNews.time", description = "Time to return all News")
    @Counted(value = "news.getAllNews.count", description = "Times news have been reurned")
    @GetMapping("/news")
    public ResponseEntity<List<News>> getAllNews(@RequestParam(required = false) String title) {
        return newsService.getAllNews(title);
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") String id) {
        return newsService.getNewsById(id);
    }
    @PostMapping("/news")
    @Counted(value = "news.createdNews.count", description = "Times news have been created")
    public ResponseEntity<News> createNews(@RequestBody News news) {
        return newsService.createNews(news);
    }
    @PutMapping("/news/{id}")
    public ResponseEntity<News> updateNews(@PathVariable("id") String id, @RequestBody News news) {
        return newsService.updateNews(id, news);
    }
    @DeleteMapping("/news/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id") String id) {
        return newsService.deleteNews(id);
    }
    @DeleteMapping("/news")
    public ResponseEntity<HttpStatus> deleteAllNews() {
        return newsService.deleteAllNews();
    }
    @GetMapping("/news/published")
    public ResponseEntity<List<News>> findByPublished() {
        return newsService.findByPublished();
    }


}