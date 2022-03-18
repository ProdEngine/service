package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.Donation;
import ro.unibuc.hello.data.News;
import ro.unibuc.hello.services.DonationService;
import ro.unibuc.hello.services.NewsService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class DonationControllerTest {
    @Mock
    private DonationService donationService;

    @InjectMocks
    private DonationController donationController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    Donation donation;
    Donation createdDonation;
    Integer sum=30;
    ArrayList<Donation> donationList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(donationController).build();
        objectMapper = new ObjectMapper();

        donation = new Donation("Donation", 30, false);
        donation.setId("882r653ht3ds03cd1c832ss");
        createdDonation = new Donation("Donation", 0, true);

        donationList = new ArrayList<>();
        donationList.add(donation);
    }


    @Test
    void getAllDonations() throws Exception {
        when(donationController.getAllDonations(null)).thenReturn(new ResponseEntity<>(donationList, HttpStatus.OK));

        MvcResult result = mockMvc.perform(get("/donations")
                        .content(objectMapper.writeValueAsString(donationList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(donationList));
    }
    @Test
    void getDonationById() throws Exception {
        when(donationController.getDonationById(donation.getId())).thenReturn(new ResponseEntity<>(donation, HttpStatus.OK));

        MvcResult result = mockMvc.perform(get("/donations/882r653ht3ds03cd1c832ss")
                        .content(objectMapper.writeValueAsString(donation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(donation));
    }

    @Test
   void getSum() throws Exception {
        when(donationController.getSum(null)).thenReturn(new ResponseEntity<>(sum, HttpStatus.OK));

       MvcResult result = mockMvc.perform(get("/sum")
                        .content(objectMapper.writeValueAsString(sum))
                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(sum));
    }

    @Test
    void createDonation() throws Exception {
        when(donationController.createDonation(createdDonation)).thenReturn(new ResponseEntity<>(createdDonation, HttpStatus.CREATED));

        MvcResult result = mockMvc.perform(post("/donations")
                        .content(objectMapper.writeValueAsString(createdDonation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getStatus(), 200);
    }

    @Test
    void deleteDonation() throws Exception {
        when(donationController.deleteDonation(donation.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        MvcResult result = mockMvc.perform(delete("/donations/882r653ht3ds03cd1c832ss")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        assertEquals(result.getResponse().getStatus(), 204);
    }
    @Test
    void deleteAllDonations() throws Exception {
        when(donationController.deleteAllDonations()).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        MvcResult result = mockMvc.perform(delete("/donations")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        assertEquals(result.getResponse().getStatus(), 204);


    }
    @Test
    void findByAnonimity() throws Exception {
        when(donationController.findByAnonymity()).thenReturn(new ResponseEntity<>(donationList, HttpStatus.OK));

        MvcResult result = mockMvc.perform(get("/donations/public")
                        .content(objectMapper.writeValueAsString(donationList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(donationList));

    }


}