package ro.unibuc.hello.controller;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.Donation;
import ro.unibuc.hello.data.DonationRepository;
import ro.unibuc.hello.services.DonationService;
import java.util.concurrent.atomic.AtomicLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
public class DonationController {
    //@Autowired
    //MeterRegistry metricsRegistry;

    @Autowired
    DonationService donationService;

    @GetMapping("/donations")
    @Timed(value = "donations.getAllDonations.time", description = "Time to return all donations")
    @Counted(value = "donations.getAllDonations.count", description = "Times donations have been reurned")
    public ResponseEntity<List<Donation>> getAllDonations(@RequestParam(required = false) String sender) {
        //metricsRegistry.counter("my_non_aop_metric", "endpoint", "donations").increment(counter.incrementAndGet());
    return donationService.getAllDonations(sender);
    }
    @Counted(value = "donations.getSum.count", description = "Times sum was calculated")
    @GetMapping("/sum")
    public ResponseEntity<Integer> getSum(@RequestParam(required = false) String sender) {
        return donationService.getSum(sender);
    }
    @GetMapping("/donations/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable("id") String id) {
       return donationService.getDonationById(id);

    }
    @Counted(value = "donations.createdDonations.count", description = "Donations Created")
    @PostMapping("/donations")
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
       return donationService.createDonation(donation);
    }
    @DeleteMapping("/donations/{id}")
    public ResponseEntity<HttpStatus> deleteDonation(@PathVariable("id") String id) {
       return donationService.deleteDonation(id);
    }
    @DeleteMapping("/donations")
    public ResponseEntity<HttpStatus> deleteAllDonations() {
        return donationService.deleteAllDonations();
    }
    @GetMapping("/donations/public")
    public ResponseEntity<List<Donation>> findByAnonymity() {
      return donationService.findByAnonymity();

    }

}
