package com.ruben.sigebi.application.interfaces;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

public interface Scheduler {
    void execute();
}
