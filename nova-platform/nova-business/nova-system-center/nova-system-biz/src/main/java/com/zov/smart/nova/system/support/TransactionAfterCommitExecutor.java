package com.zov.smart.nova.system.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Executes cache invalidation only after the surrounding write transaction commits.
 */
public final class TransactionAfterCommitExecutor {

    private static final Logger log = LoggerFactory.getLogger(TransactionAfterCommitExecutor.class);

    private TransactionAfterCommitExecutor() {
    }

    public static void runAfterCommit(Runnable action) {
        if (action == null) {
            return;
        }
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            safeRun(action);
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                safeRun(action);
            }
        });
    }

    private static void safeRun(Runnable action) {
        try {
            action.run();
        } catch (Exception ex) {
            log.warn("Post-commit cache invalidation failed", ex);
        }
    }
}
