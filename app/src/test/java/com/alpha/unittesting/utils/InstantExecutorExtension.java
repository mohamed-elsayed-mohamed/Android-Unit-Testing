package com.alpha.unittesting.utils;


import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class InstantExecutorExtension implements AfterEachCallback, BeforeEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        ArchTaskExecutor.getInstance().setDelegate(null);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ArchTaskExecutor.getInstance()
                .setDelegate(new TaskExecutor() {
                    @Override
                    public void executeOnDiskIO(@org.jetbrains.annotations.NotNull Runnable runnable) {
                        runnable.run();
                    }

                    @Override
                    public void postToMainThread(@org.jetbrains.annotations.NotNull Runnable runnable) {
                        runnable.run();
                    }

                    @Override
                    public boolean isMainThread() {
                        return true;
                    }
                });
    }
}
