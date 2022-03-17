package ro.unibuc.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.News;
import ro.unibuc.hello.data.NewsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    public ResponseEntity<List<News>> getAllNews(String title) {
        try {
            List<News> news = new ArrayList<News>();
            if (title == null)
                newsRepository.findAll().forEach(news::add);
            else{
                newsRepository.findByTitleContaining(title).forEach(news::add);
            }
            if (news.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (Exception e) {return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);}
    }
    public ResponseEntity<News> getNewsById(String id) {
        Optional<News> newsData = newsRepository.findById(id);
        if (newsData.isPresent()) {
            return new ResponseEntity<>(newsData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<News> createNews(News news) {
        try {
            News _news = newsRepository.save(new News(news.getTitle(), news.getDescription(), news.isPublished()));
            return new ResponseEntity<>(_news, HttpStatus.CREATED);
        } catch (Exception e) {return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);}
    }
    public ResponseEntity<News> updateNews(String id,News news) {
        Optional<News> newsData = newsRepository.findById(id);
        if (newsData.isPresent()) {
            News _news = newsData.get();
            _news.setTitle(news.getTitle());
            _news.setDescription(news.getDescription());
            _news.setPublished(news.isPublished());
            return new ResponseEntity<>(newsRepository.save(_news), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<HttpStatus> deleteNews(String id) {
        try {
            newsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }
    public ResponseEntity<HttpStatus> deleteAllNews() {
        try {
            newsRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }
    public ResponseEntity<List<News>> findByPublished() {
        try {
            List<News> news = newsRepository.findByPublished(true);
            if (news.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (Exception e) {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }
}
