package com.nowak.kamil.hibernatejavamapping.repository;

import com.nowak.kamil.hibernatejavamapping.common.service.EncryptionService;
import com.nowak.kamil.hibernatejavamapping.creditcard.domain.CreditCard;
import com.nowak.kamil.hibernatejavamapping.creditcard.repository.CreditCardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositoryTest {

    final String CREDIT_CARD = "12324444";

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EncryptionService encryptionService;

    @Test
    void shouldSaveCreditCard() {
        final var creditCard = new CreditCard();
        creditCard.setCreditCardNumber(CREDIT_CARD);
        creditCard.setCvv("123");
        creditCard.setExpirationDate("12/2030");
        final var savedCreditCard = creditCardRepository.saveAndFlush(creditCard);


        final var dbRow = jdbcTemplate.queryForMap("""
            SELECT * FROM credit_card
                WHERE id = """ + savedCreditCard.getId());

        final var cardValue = (String) dbRow.get("credit_card_number");

        assertThat(savedCreditCard.getCreditCardNumber()).isNotEqualTo(cardValue);
        assertThat(cardValue).isEqualTo(encryptionService.encrypt(CREDIT_CARD));
        assertThat(savedCreditCard.getVersion()).isEqualTo(0);
    }
}