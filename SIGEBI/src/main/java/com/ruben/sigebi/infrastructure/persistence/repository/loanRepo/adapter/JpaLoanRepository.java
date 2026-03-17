package com.ruben.sigebi.infrastructure.persistence.repository.loanRepo.adapter;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;
import com.ruben.sigebi.infrastructure.persistence.mapper.LoanMapper;
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
    private final SpringDataResourceRepository resourceRepository;
    private final SpringDataLoanRepository repository;

    public JpaLoanRepository(SpringDataUserRepository userRepository, SpringDataResourceRepository resourceRepository, SpringDataLoanRepository repository) {
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
        this.repository = repository;
    }

    @Override
    public List<Loan> findLoansByUserStateAndStatus(UserId userId, PendingState pendingState, Status status) {
        return repository.findLoansByUserStateAndStatus(userId.value().toString(),pendingState.name(),status.name())
                .stream()
                .map(LoanMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findLoansByStateAndStatus(PendingState pendingState, Status status) {
        return repository.findLoansByStateAndStatus(
                        pendingState.name(),
                        status.name()
                )
                .stream()
                .map(LoanMapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findAllActiveOverdueLoans() {
        return repository.findAllActiveOverdueLoans("ACTIVE", "OVERDUE")
                .stream()
                .map(LoanMapper::toDomain)
                .toList();
    }


    @Override
    public void save(Loan loan) {

        UserEntity user = userRepository
                .findById(loan.getUserId().value())
                .orElseThrow();

        BibliographyResourceEntity resource = resourceRepository
                .findById(loan.getResourceId().value())
                .orElseThrow();

        LoanEntity entity = LoanMapper.toEntity(loan, user, resource);

        repository.save(entity);
    }

    @Override
    public Optional<Loan> findById(LoanId loanId) {
        return repository.findById(loanId.loanID())
                .map(LoanMapper::toDomain);
    }

    @Override
    public Set<Loan> findByStatus(Status status) {
        return repository.findByStatus(status.name())
                .stream()
                .map(LoanMapper::toDomain)
                .collect(Collectors.toSet());
    }


    @Override
    public Set<Loan> findByStatusAndUser(Status status, UserId userId) {
        return repository.findByStatusAndUser(
                        status.name(),
                        userId.value().toString()
                )
                .stream()
                .map(LoanMapper::toDomain)
                .collect(Collectors.toSet());
    }



    @Override
    public Set<Loan> findByUser(UserId userId) {
        return repository.findByUserId(userId.value())
                .stream()
                .map(LoanMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Loan> findAllActiveLoans() {
        return repository.findAllActiveLoans("ACTIVE")
                .stream()
                .map(LoanMapper::toDomain)
                .collect(Collectors.toSet());

    }
}