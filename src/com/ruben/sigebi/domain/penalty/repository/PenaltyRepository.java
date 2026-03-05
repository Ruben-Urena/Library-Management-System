package com.ruben.sigebi.domain.penalty.repository;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import java.util.List;

public interface PenaltyRepository {
    void save(Penalty penalty);
    List<Penalty> findUnpaidByUserId(UserId userId);
}