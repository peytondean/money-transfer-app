package com.techelevator.repo;

import com.techelevator.tenmo.TenmoApplication;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.repo.TransferRepo;
import com.techelevator.tenmo.repo.TransferStatusRepo;
import com.techelevator.tenmo.repo.TransferTypeRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ContextConfiguration(classes = TenmoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransferRepositoryTest {

    @Autowired
    TransferRepo transferRepo;

    @Autowired
    TransferTypeRepo transferTypeRepo;

    @Autowired
    TransferStatusRepo transferStatusRepo;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void ContextLoads(){}

    @Test
    void TestGetTransfers() {

        Optional<TransferType> testTransferTypeOpt1 =  transferTypeRepo.findById(2);
        Optional<TransferType> testTransferTypeOpt2 = transferTypeRepo.findById(1);
        Optional<TransferStatus> testTransferStatusOpt1 = transferStatusRepo.findById(2);
        Optional<TransferStatus> testTransferStatusOpt2 = transferStatusRepo.findById(1);
        TransferType testTransferType1 = new TransferType();
        TransferType testTransferType2 = new TransferType();
        TransferStatus testTransferStatus1 = new TransferStatus();
        TransferStatus testTransferStatus2 = new TransferStatus();

        if (testTransferTypeOpt1.isPresent()) {
            testTransferType1 = testTransferTypeOpt1.get();
            testEntityManager.persistAndFlush(testTransferType1);
        }
        if (testTransferStatusOpt1.isPresent()) {
            testTransferStatus1 = testTransferStatusOpt1.get();
            testEntityManager.persistAndFlush(testTransferStatus1);
        }

        Transfer testTransfer1 = new Transfer();

        testTransfer1.setTransferId(3034);
        testTransfer1.setAccountFrom(2001);
        testTransfer1.setAccountTo(2001);
        testTransfer1.setAmount(BigDecimal.valueOf(100));
        testTransfer1.setTransferType(testTransferType1);
        testTransfer1.setTransferStatus(testTransferStatus1);

        testEntityManager.persistAndFlush(testTransfer1);

        if (testTransferTypeOpt2.isPresent()) {
            testTransferType2 = testEntityManager.persistAndFlush(testTransferTypeOpt2.get());
        }
        //testEntityManager.merge(testTransferStatus1);
        if (testTransferStatusOpt2.isPresent()) {
            testTransferStatus2 = testEntityManager.persistAndFlush(testTransferStatusOpt2.get());
        }

        Transfer testTransfer2 = Transfer.builder()
                .transferId(3041)
                .accountFrom(2002)
                .accountTo(2001)
                .amount(BigDecimal.valueOf(100))
                .transferType(testTransferType2)
                .transferStatus(testTransferStatus2)
                .build();

        testEntityManager.persistAndFlush(testTransfer2);

        List<Transfer> transfers = transferRepo.findAll();

        Assertions.assertThat(transfers.size()).isEqualTo(2);
        Assertions.assertThat(transfers).extracting(Transfer::getTransferId)
                .containsExactlyInAnyOrder(testTransfer1.getTransferId(), testTransfer2.getTransferId());
    }

    @Test
    void TestGetTransfersById(){

    }

    @Test
    void TestGetPendingTransfersById() {

    }

    @Test
    void testCreateTransfer() {

    }

    @Test
    void testDeleteTransfer() {

    }

    @Test
    void testGetTransfersByUserId () {

    }

    @Test
    void testApproveTransfer() {

    }

    @Test
    void TestRejectTransfer() {

    }

    @Test
    void TestSendTeBucks(){

    }

    @Test
    void TestRequestTeBucks() {

    }

}
