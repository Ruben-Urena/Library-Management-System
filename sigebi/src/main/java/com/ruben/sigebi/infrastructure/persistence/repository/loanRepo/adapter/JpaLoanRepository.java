package com.ruben.sigebi.infrastructure.persistence.repository.loanRepo.adapter;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.ResourceCopyEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;
import com.ruben.sigebi.infrastructure.persistence.mapper.LoanMapper;
import com.ruben.sigebi.infrastructure.persistence.repository.ResourceCopyRepo.SpringDataResourceCopyRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.bookRepo.SpringDataResourceRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.loanRepo.SpringDataLoanRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.userRepo.SpringDataUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class JpaLoanRepository implements LoanRepository {
    private final SpringDataUserRepository userRepository;
    private final SpringDataResourceCopyRepository copyRepository;
    private final SpringDataLoanRepository repository;

    public JpaLoanRepository(
            SpringDataUserRepository userRepository,
            SpringDataResourceCopyRepository copyRepository,
            SpringDataLoanRepository repository
    ) {
        this.userRepository = userRepository;
        this.copyRepository = copyRepository;
        this.repository = repository;
    }

    @Override
    public void save(Loan loan) {
        UserEntity user = userRepository
                .findById(loan.getUserId().value())
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                        "User not found: " + loan.getUserId().value()));

        ResourceCopyEntity resourceCopy = copyRepository
                .findById(loan.getCopyId().value())
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                        "ResourceCopy not found: " + loan.getCopyId().value()));

        repository.save(LoanMapper.toEntity(loan, user, resourceCopy));
    }

    @Override
    public Optional<Loan> findById(LoanId loanId) {
        return repository.findById(loanId.loanID())
                .map(LoanMapper::toDomain);
    }

    @Override
    public Set<Loan> findByUser(UserId userId) {
        return repository.findByUser_Id(userId.value())
                .stream()
                .map(LoanMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Loan> findLoansByUserStateAndStatus(UserId userId, PendingState pendingState, Status status) {
        return repository.findLoansByUserStateAndStatus(userId.value(), pendingState, status)
                .stream()
                .map(LoanMapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findLoansByStateAndStatus(PendingState pendingState, Status status) {
        return repository.findLoansByPendingStateAndStatus(pendingState, status)
                .stream()
                .map(LoanMapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findAllActiveOverdueLoans() {
        return repository.findAllActiveOverdueLoans(Status.ACTIVE, PendingState.OVERDUE)
                .stream()
                .map(LoanMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Loan> findByStatus(Status status) {
        return repository.findByStatus(status)
                .map(LoanMapper::toDomain);
    }

    @Override
    public Set<Loan> findAllActiveLoans() {
        return repository.findAllActiveLoans(Status.ACTIVE)
                .stream()
                .map(LoanMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Loan> findByStatusAndUserId(Status status, UserId userId) {
        return repository.findByStatusAndUserId(status, userId.value())
                .stream()
                .map(LoanMapper::toDomain)
                .collect(Collectors.toSet());
    }
}