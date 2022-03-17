package ro.unibuc.hello.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.data.News;
import ro.unibuc.hello.data.NewsRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@SpringBootTest
class NewsServiceTest {

    @Mock
    NewsRepository newsRepository;

    @InjectMocks
    NewsService newsService = new NewsService();

    News news;
    News newsWithoutId;
    News updatedNews;
    News newsContainsTitle;
    News nonExistentNews;
    ArrayList<News> newsListContainingTitle;
    ArrayList<News> newsList;
    ArrayList<News> publishedNews;
    ArrayList<News> emptyNews;


    @BeforeEach
    void setUp() {
        news = new News("News", "Description", true);
        newsWithoutId = new News("NewsWithoutId", "DescriptionWithoutId", true);
        updatedNews = new News("Updated News", "Updated Description", false);
        newsContainsTitle = new News("First News", "First Description", true);
        nonExistentNews = new News("Non Existent News", "Non existent description", false);
        nonExistentNews.setId("622b18512929603cd1c844cc");
        news.setId("622b18512929603cd1c844cb");
        updatedNews.setId("622b18512929603cd1c844cb");
        newsList = new ArrayList<>();
        newsListContainingTitle = new ArrayList<>();
        publishedNews = new ArrayList<>();
        emptyNews = new ArrayList<>();
        newsList.add(news);
        newsList.add(newsContainsTitle);
        newsListContainingTitle.add(newsContainsTitle);
        publishedNews.add(news);
        publishedNews.add(newsWithoutId);
        publishedNews.add(newsContainsTitle);
    }

    @Test
    void getAllNews() {
        doReturn(newsList).when(newsRepository).findAll();
        ResponseEntity<List<News>> result1 = newsService.getAllNews(null);
        Assertions.assertEquals(result1, new ResponseEntity<>(newsList, HttpStatus.OK));

        doReturn(emptyNews).when(newsRepository).findAll();
        ResponseEntity<List<News>> result2 = newsService.getAllNews(null);
        Assertions.assertEquals(result2, new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
    @Test
    void getAllNewsContainingTitle() {
        doReturn(newsListContainingTitle).when(newsRepository).findByTitleContaining("First");
        ResponseEntity<List<News>> result = newsService.getAllNews("First");
        Assertions.assertEquals(result, new ResponseEntity<>(newsListContainingTitle, HttpStatus.OK));
    }

    @Test
    void getNewsById() {
        when(newsRepository.findById(news.getId())).thenReturn(Optional.ofNullable(news));
        ResponseEntity<News> result1 = newsService.getNewsById(news.getId());
        Assertions.assertEquals(result1, new ResponseEntity<>(news, HttpStatus.OK));

        when(newsRepository.findById(nonExistentNews.getId())).thenReturn(Optional.empty());
        ResponseEntity<News> result2 = newsService.getNewsById(nonExistentNews.getId());
        Assertions.assertEquals(result2, new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Test
    void createNews() {
        // todo solve issue
        when(newsRepository.save(newsWithoutId)).thenReturn(news);
        ResponseEntity<News> result = newsService.createNews(news);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void updateNews() {
        when(newsRepository.findById(news.getId())).thenReturn(Optional.ofNullable(news));
        when(newsRepository.save(updatedNews)).thenReturn(updatedNews);
        ResponseEntity<News> result1 = newsService.updateNews(news.getId(), updatedNews);
        Assertions.assertEquals(result1.getStatusCode(), HttpStatus.OK);

        when(newsRepository.findById(nonExistentNews.getId())).thenReturn(Optional.empty());
        ResponseEntity<News> result2 = newsService.updateNews(nonExistentNews.getId(), nonExistentNews);
        Assertions.assertEquals(result2, new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Test
    void deleteNews() {
        ResponseEntity<HttpStatus> result = newsService.deleteNews(news.getId());
        Assertions.assertEquals(result, new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void deleteAllNews() {
        ResponseEntity<HttpStatus> result = newsService.deleteAllNews();
        Assertions.assertEquals(result, new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void findByPublished() {
        doReturn(publishedNews).when(newsRepository).findByPublished(true);
        ResponseEntity<List<News>> result1 = newsService.findByPublished();
        Assertions.assertEquals(result1, new ResponseEntity<>(publishedNews, HttpStatus.OK));

        doReturn(emptyNews).when(newsRepository).findByPublished(true);
        ResponseEntity<List<News>> result2 = newsService.findByPublished();
        Assertions.assertEquals(result2, new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }
}