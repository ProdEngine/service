package ro.unibuc.hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.News;
import ro.unibuc.hello.services.NewsService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class NewsControllerTest {
    @Mock
    private NewsService newsService;

    @InjectMocks
    private NewsController newsController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    News news;
    News updatedNews;
    News createdNews;
    ArrayList<News> newsList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
        objectMapper = new ObjectMapper();

        news = new News("News", "Description", true);
        news.setId("622b18512929603cd1c844cb");
        createdNews = new News("Created News", "New Description", true);
        updatedNews = new News("Updated News", "Updated Description", false);

        newsList = new ArrayList<>();
        newsList.add(news);
    }

    @Test
    void getAllNews() throws Exception {
        when(newsService.getAllNews(null)).thenReturn(new ResponseEntity<>(newsList, HttpStatus.OK));

        MvcResult result = mockMvc.perform(get("/news")
                        .content(objectMapper.writeValueAsString(newsList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(newsList));
    }

    @Test
    void getNewsById() throws Exception {
        when(newsService.getNewsById(news.getId())).thenReturn(new ResponseEntity<>(news, HttpStatus.OK));

        MvcResult result = mockMvc.perform(get("/news/622b18512929603cd1c844cb")
                        .content(objectMapper.writeValueAsString(news))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(news));
    }

    @Test
    void createNews() throws Exception {
        when(newsService.createNews(createdNews)).thenReturn(new ResponseEntity<>(createdNews, HttpStatus.CREATED));

        MvcResult result = mockMvc.perform(post("/news")
                        .content(objectMapper.writeValueAsString(createdNews))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getStatus(), 200);
    }

    @Test
    void updateNews() throws Exception {
        when(newsService.updateNews(news.getId(), updatedNews)).thenReturn(new ResponseEntity<>(updatedNews, HttpStatus.OK));

        MvcResult result = mockMvc.perform(put("/news/622b18512929603cd1c844cb")
                        .content(objectMapper.writeValueAsString(updatedNews))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getStatus(), 200);
    }

    @Test
    void deleteNews() throws Exception {
        when(newsService.deleteNews(news.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        MvcResult result = mockMvc.perform(delete("/news/622b18512929603cd1c844cb")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        assertEquals(result.getResponse().getStatus(), 204);
    }

    @Test
    void deleteAllNews() throws Exception {
        when(newsService.deleteAllNews()).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        MvcResult result = mockMvc.perform(delete("/news")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        assertEquals(result.getResponse().getStatus(), 204);
    }

    @Test
    void findByPublished() throws Exception {
        when(newsService.findByPublished()).thenReturn(new ResponseEntity<>(newsList, HttpStatus.OK));

        MvcResult result = mockMvc.perform(get("/news/published")
                        .content(objectMapper.writeValueAsString(newsList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(newsList));

    }
}