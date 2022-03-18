package ro.unibuc.hello.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DonationTest {
    String id;
    String sender;
    Integer amount;
    boolean anonymity;
    Donation donation1;
    Donation donation2;
    String donationString;
    int hash;

    @BeforeEach
    void setUp() {
        donation1 = new Donation();
        id = "882r653ht3ds03cd1c832ss";
        sender = "Sender";
        amount = 30;
        anonymity = false;
        donationString = "Donation [id=" + id + ", sender=" + sender + ", amount=" + amount + ", anonymity" + anonymity + "]";
        hash = Objects.hash(id, sender, amount, anonymity);
        donation2 = new Donation(sender, amount, anonymity);
        donation2.setId(id);

        donation1.setId(id);
        donation1.setSender(sender);
        donation1.setAmount(amount);
        donation1.setAnonymity(anonymity);
    }
        @Test
        void getId() {
            assertEquals(id, donation1.getId());
            assertEquals(id, donation2.getId());
        }

        @Test
        void getSender() {
            assertEquals(sender, donation1.getSender());
            assertEquals(sender, donation2.getSender());
        }

        @Test
        void getAmount() {
            assertEquals(amount, donation1.getAmount());
            assertEquals(amount, donation2.getAmount());
        }

        @Test
        void isAnonymous() {
            assertEquals(anonymity, donation1.isAnonymous());
            assertEquals(anonymity, donation2.isAnonymous());
        }



    }


