package com.zov.smart.nova.infra.mybatisplus.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionHelperTest {

    @Test
    void shouldExecuteSupplierInTransaction() {
        RecordingTransactionManager transactionManager = new RecordingTransactionManager();
        TransactionHelper helper = new TransactionHelper(new TransactionTemplate(transactionManager));

        String value = helper.execute(() -> "ok");

        assertEquals("ok", value);
        assertEquals(1, transactionManager.getCommitCount());
    }

    @Test
    void shouldRollbackWhenActionThrows() {
        RecordingTransactionManager transactionManager = new RecordingTransactionManager();
        TransactionHelper helper = new TransactionHelper(new TransactionTemplate(transactionManager));

        assertThrows(IllegalStateException.class, () -> helper.executeWithoutResult(() -> {
            throw new IllegalStateException("boom");
        }));
        assertEquals(1, transactionManager.getRollbackCount());
    }

    @Test
    void shouldRunAfterCommitImmediatelyWhenNoTransactionSynchronization() {
        TransactionHelper helper = new TransactionHelper(new TransactionTemplate(new RecordingTransactionManager()));
        AtomicBoolean executed = new AtomicBoolean(false);

        helper.afterCommit(() -> executed.set(true));

        assertTrue(executed.get());
    }

    static class RecordingTransactionManager extends AbstractPlatformTransactionManager {
        private int commitCount;
        private int rollbackCount;

        @Override
        protected Object doGetTransaction() throws TransactionException {
            return new Object();
        }

        @Override
        protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
        }

        @Override
        protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
            commitCount++;
        }

        @Override
        protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
            rollbackCount++;
        }

        int getCommitCount() {
            return commitCount;
        }

        int getRollbackCount() {
            return rollbackCount;
        }
    }
}
