package com.techelevator.repo;

import com.techelevator.tenmo.TenmoApplication;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.repo.TransferStatusRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ContextConfiguration(classes = TenmoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransferStatusRepositoryTest {

    @Autowired
    private TransferStatusRepo transferStatusRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void contextLoads(){}

    @Test
    public void testFindAll() {

        TransferStatus pending = new TransferStatus();
        pending.setTransferStatusDescription("Pending");
        testEntityManager.persistAndFlush(pending);

        TransferStatus approved = new TransferStatus();
        approved.setTransferStatusDescription("Approved");
        testEntityManager.persistAndFlush(approved);

        TransferStatus rejected = new TransferStatus();
        rejected.setTransferStatusDescription("Rejected");
        testEntityManager.persistAndFlush(rejected);

        List<TransferStatus> transferStatuses = transferStatusRepo.findAll();

        Assertions.assertThat(transferStatuses.size()).isEqualTo(3);
        Assertions.assertThat(transferStatuses).extracting(TransferStatus::getTransferStatusDescription).containsExactlyInAnyOrder("Pending", "Approved", "Rejected");
    }

    @Test
    public void TestFindById() {
        final int TRANSFER_STATUS_PENDING = 1;

        TransferStatus pending = new TransferStatus();
        pending.setTransferStatusDescription("Pending");
        testEntityManager.persistAndFlush(pending);

        Optional<TransferStatus> transferStatus = transferStatusRepo.findById(TRANSFER_STATUS_PENDING);

        Assertions.assertThat(transferStatus.isPresent()).isTrue();
        Assertions.assertThat(transferStatus.get().getTransferStatusDescription()).isEqualTo("Pending");
    }
}
