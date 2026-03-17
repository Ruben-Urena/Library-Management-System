package com.ruben.sigebi.infrastructure.persistence.entity.penalty;

import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "penalties")
public class PenaltyEntity {

    @Id
    private String id;

    private String description;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @JoinColumn(name = "user_id")
    private UserEntity user;

    @JoinColumn(name = "loan_id")
    private LoanEntity loan;

    public PenaltyEntity() {}

    public PenaltyEntity(String id, String description, Instant startDate, Instant endDate, UserEntity user, LoanEntity loan) {
        this.id = id;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.loan = loan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LoanEntity getLoan() {
        return loan;
    }

    public void setLoan(LoanEntity loan) {
        this.loan = loan;
    }

}