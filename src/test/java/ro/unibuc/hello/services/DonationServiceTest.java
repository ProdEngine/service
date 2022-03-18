package ro.unibuc.hello.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.data.Donation;
import ro.unibuc.hello.data.DonationRepository;
import ro.unibuc.hello.data.Donation;
import ro.unibuc.hello.data.DonationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DonationServiceTest {
    @Mock
    DonationRepository donationRepository;

    @InjectMocks
    DonationService donationService = new DonationService();

    Donation donation;
    Donation donationWithoutId;
    Donation nonExistentDonation;
    ArrayList<Donation> donationList;
    ArrayList<Donation> publicDonation;
    ArrayList<Donation> emptyDonation;


    @BeforeEach
    void setUp() {
        donation = new Donation("Sender", 30, false);
        donationWithoutId = new Donation("DonationWithoutId", 0, false);
        nonExistentDonation = new Donation("Non Existent Donation", 0, false);
        nonExistentDonation.setId("882r653ht3ds03cd1c832ss");
        donation.setId("882r653ht3ds03cd1c832ss");
        donationList = new ArrayList<>();
        publicDonation = new ArrayList<>();
        emptyDonation = new ArrayList<>();
        donationList.add(donation);
        publicDonation.add(donation);
        publicDonation.add(donationWithoutId);

    }

    @Test
    void getAllDonation() {
        doReturn(donationList).when(donationRepository).findAll();
        ResponseEntity<List<Donation>> result1 = donationService.getAllDonations(null);
        Assertions.assertEquals(result1, new ResponseEntity<>(donationList, HttpStatus.OK));

        doReturn(emptyDonation).when(donationRepository).findAll();
        ResponseEntity<List<Donation>> result2 = donationService.getAllDonations(null);
        Assertions.assertEquals(result2, new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @Test
    void getDonationById() {
        when(donationRepository.findById(donation.getId())).thenReturn(Optional.ofNullable(donation));
        ResponseEntity<Donation> result1 = donationService.getDonationById(donation.getId());
        Assertions.assertEquals(result1, new ResponseEntity<>(donation, HttpStatus.OK));

        when(donationRepository.findById(nonExistentDonation.getId())).thenReturn(Optional.empty());
        ResponseEntity<Donation> result2 = donationService.getDonationById(nonExistentDonation.getId());
        Assertions.assertEquals(result2, new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Test
    void createDonation() {

        when(donationRepository.save(donationWithoutId)).thenReturn(donation);
        ResponseEntity<Donation> result = donationService.createDonation(donation);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }



    @Test
    void deleteDonation() {
        ResponseEntity<HttpStatus> result = donationService.deleteDonation(donation.getId());
        Assertions.assertEquals(result, new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void deleteAllDonations() {
        ResponseEntity<HttpStatus> result = donationService.deleteAllDonations();
        Assertions.assertEquals(result, new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void findByAnonymity() {
        doReturn(publicDonation).when(donationRepository).findByAnonymity(false);
        ResponseEntity<List<Donation>> result1 = donationService.findByAnonymity();
        Assertions.assertEquals(result1, new ResponseEntity<>(publicDonation, HttpStatus.OK));

        doReturn(emptyDonation).when(donationRepository).findByAnonymity(false);
        ResponseEntity<List<Donation>> result2 = donationService.findByAnonymity();
        Assertions.assertEquals(result2, new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }
}
