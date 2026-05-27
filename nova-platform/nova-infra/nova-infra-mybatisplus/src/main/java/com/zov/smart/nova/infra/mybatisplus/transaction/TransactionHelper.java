package com.zov.smart.nova.infra.mybatisplus.transaction;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Programmatic transaction helper.
 *
 * <p>Use this helper when a service needs an explicit small transaction scope or needs to
 * register actions after a successful commit. It wraps Spring's {@link TransactionTemplate}
 * without leaking transaction infrastructure to upper modules.</p>
 */
public class TransactionHelper {

    private final TransactionTemplate transactionTemplate;

    public TransactionHelper(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = Objects.requireNonNull(transactionTemplate, "transactionTemplate must not be null");
    }

    public <T> T execute(Supplier<T> action) {
        Objects.requireNonNull(action, "action must not be null");
        return transactionTemplate.execute(new TransactionCallback<T>() {
            @Override
            public T doInTransaction(TransactionStatus status) {
                return action.get();
            }
        });
    }

    public void executeWithoutResult(final Runnable action) {
        Objects.requireNonNull(action, "action must not be null");
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                action.run();
            }
        });
    }

    /**
     * Registers an action that runs only after the surrounding transaction commits.
     * If no transaction is active, the action runs immediately to keep callers deterministic.
     */
    public void afterCommit(final Runnable action) {
        Objects.requireNonNull(action, "action must not be null");
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            action.run();
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                action.run();
            }
        });
    }
}
