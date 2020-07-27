package com.denisalupu.freecycle.service;

import com.denisalupu.freecycle.domain.entity.TransactionEntity;
import com.denisalupu.freecycle.domain.entity.UserEntity;
import com.denisalupu.freecycle.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionEntity save(long donationId, UserEntity receiverEntity) {
        List<TransactionEntity> transactions = receiverEntity.getTransactions();
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .donationId(donationId)
                .receiver(receiverEntity)
                .build();
        transactions.add(transactionEntity);
        return transactionRepository.save(transactionEntity);
    }
}
