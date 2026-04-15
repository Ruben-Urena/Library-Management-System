package com.ruben.sigebi.infrastructure.persistence.repository.penalty.adapter;

import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.penalty.PenaltyEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;
import com.ruben.sigebi.infrastructure.persistence.mapper.PenaltyMapper;
import com.ruben.sigebi.infrastructure.persistence.repository.loanRepo.SpringDataLoanRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.penalty.SpringDataPenaltyRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.userRepo.SpringDataUserRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaPenaltyRepository implements PenaltyRepository {

    private final SpringDataPenaltyRepository springDataPenaltyRepository;
    private final SpringDataUserRepository springDataUserRepository;
    private final SpringDataLoanRepository springDataLoanRepository;

    public JpaPenaltyRepository(SpringDataPenaltyRepository springDataPenaltyRepository,
                                SpringDataUserRepository springDataUserRepository,
                                SpringDataLoanRepository springDataLoanRepository) {
        this.springDataPenaltyRepository = springDataPenaltyRepository;
        this.springDataUserRepository = springDataUserRepository;
        this.springDataLoanRepository = springDataLoanRepository;
    }

    @Override
    public Optional<Penalty> findById(PenaltyId penaltyId) {
        return springDataPenaltyRepository
                .findById(penaltyId.value())
                .map(PenaltyMapper::toDomain);
    }

    @Override
    public void save(Penalty penalty) {
        UserEntity user = springDataUserRepository.findById(penalty.getUserId().value())
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                        "User not found: " + penalty.getUserId()));

        LoanEntity loan = springDataLoanRepository.findById(penalty.getLoanId().loanID())
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                        "Loan not found: " + penalty.getLoanId()));

        PenaltyEntity entity = PenaltyMapper.toEntity(penalty, user, loan);
        springDataPenaltyRepository.save(entity);
    }

    @Override
    public Optional<Penalty> findActivePenaltyByLoan(LoanId loanId, UserId userId) {
        return springDataPenaltyRepository
                .findByLoanIdAndUserIdAndStatus(loanId.loanID(), userId.value(), Status.ACTIVE)
                .map(PenaltyMapper::toDomain);
    }

    @Override
    public Optional<List<Penalty>> findPenaltyByUserId(UserId userId) {
        List<Penalty> penalties = springDataPenaltyRepository
                .findByUserId(userId.value())
                .stream()
                .map(PenaltyMapper::toDomain)
                .toList();
        return penalties.isEmpty() ? Optional.empty() : Optional.of(penalties);
    }

    @Override
    public List<Penalty> findAllActiveAndDueDatePenalty() {
        return springDataPenaltyRepository
                .findByStatusAndEndDateBefore(Status.ACTIVE, Instant.now())
                .stream()
                .map(PenaltyMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<List<Penalty>> findPenaltyByCopyId(ResourceCopyId copyId) {

        List<Penalty> penalties = springDataPenaltyRepository
                .findByCopyId(copyId.value())
                .stream()
                .map(PenaltyMapper::toDomain)
                .toList();
        return penalties.isEmpty() ? Optional.empty() : Optional.of(penalties);
    }

    @Override
    public List<Penalty> findByStatusAndUserId(Status status, UserId userId) {
        List<Penalty> penalties = springDataPenaltyRepository
                .findByStatusAndUserId(status,userId.value())
                .stream()
                .map(PenaltyMapper::toDomain)
                .toList();
        return penalties.isEmpty() ? Collections.emptyList() : penalties;
    }


}




