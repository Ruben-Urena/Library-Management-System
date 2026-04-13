package com.ruben.sigebi.domain.common.interfaces;
import java.util.Optional;
import java.util.UUID;

public interface DomainRepository <T, I>{
    void save(T t);
    Optional<T> findById(I id);
}
