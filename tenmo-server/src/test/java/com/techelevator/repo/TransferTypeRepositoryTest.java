package com.techelevator.repo;

import com.techelevator.tenmo.TenmoApplication;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.repo.TransferTypeRepo;
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
public class TransferTypeRepositoryTest {

    @Autowired
    private TransferTypeRepo transferTypeRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void contextLoads(){}

    @Test
    public void testFindAll() {
        TransferType request = new TransferType();
        request.setTransferTypeDescription("Request");
        testEntityManager.persistAndFlush(request);

        TransferType send = new TransferType();
        send.setTransferTypeDescription("Send");
        testEntityManager.persistAndFlush(send);

        List<TransferType> transferTypes = transferTypeRepo.findAll();

        Assertions.assertThat(transferTypes.size()).isEqualTo(2);
        Assertions.assertThat(transferTypes).extracting(TransferType::getTransferTypeDescription).containsExactlyInAnyOrder("Request", "Send");
    }

    @Test
    public void TestFindById() {
        final int TRANSFER_TYPE_SEND = 2;

        TransferType request = new TransferType();
        request.setTransferTypeDescription("Request");
        testEntityManager.persistAndFlush(request);

        TransferType send = new TransferType();
        send.setTransferTypeDescription("Send");
        testEntityManager.persistAndFlush(send);

        Optional<TransferType> transferType = transferTypeRepo.findById(TRANSFER_TYPE_SEND);

        Assertions.assertThat(transferType.isPresent()).isTrue();
        Assertions.assertThat(transferType.get().getTransferTypeDescription()).isEqualTo("Send");
    }
}
