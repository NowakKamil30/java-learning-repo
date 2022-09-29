package com.nowak.kamil.hibernatejavamapping.repository;

import com.nowak.kamil.hibernatejavamapping.domain.OrderApproval;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderApprovalRepositoryTest {

    @Autowired
    OrderApprovalRepository orderApprovalRepository;

    @Test
    void orphanRemovalHowWorks() {
        final var orderApproval = OrderApproval.builder()
                .approvedBy("me")
                .build();

        final var save = orderApprovalRepository.save(orderApproval);

        assertNotNull(save);
        assertNotNull(save.getId());
        assertNull(save.getOrderHeader());
    }



}