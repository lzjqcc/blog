package com.lzj.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class TransactionHelper {
    @Autowired
    private PlatformTransactionManager transactionManager;
    public TransactionStatus beginTransaction(TransactionDefinition definition) {
        return transactionManager.getTransaction(definition);
    }
    public TransactionStatus beginTransaction() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        return transactionManager.getTransaction(definition);
    }
    public void commit(TransactionStatus status) {
        transactionManager.commit(status);
    }
    public void rollback(TransactionStatus status) {
        transactionManager.rollback(status);
    }
}
