package ro.unibuc.hello.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.data.Donation;
import ro.unibuc.hello.data.DonationRepository;

@SpringBootTest
@Tag("IT")
public class DonationServiceTestIT {
    @Autowired
    DonationRepository donationRepository;

    @Autowired
    DonationService donationService;

    Donation donation;
    String id;

    @BeforeEach
    void setUp(){
        id = "882r653ht3ds03cd1c832ss";
        donation = new Donation("sender",30,false);
        donation.setId(id);
    }

    @Test
    void createDonation(){
        ResponseEntity<Donation> result = donationService.createDonation(donation);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void deleteDonation(){
        donationRepository.save(donation);
        ResponseEntity<HttpStatus> result = donationService.deleteDonation(donation.getId());
        Assertions.assertEquals(result, new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
