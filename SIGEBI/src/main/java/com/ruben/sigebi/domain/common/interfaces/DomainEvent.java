package com.ruben.sigebi.domain.common.interfaces;
import java.time.Instant;

public interface DomainEvent {
    Instant occurredOn();
}