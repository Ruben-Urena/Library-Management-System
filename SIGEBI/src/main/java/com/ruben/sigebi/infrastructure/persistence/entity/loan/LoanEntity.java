package com.ruben.sigebi.infrastructure.persistence.entity.loan;

import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "loans")
public class LoanEntity {

    @Id
    private UUID id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "due_date")
    private Instant dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "pending_state")
    private PendingState pendingState;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private BibliographyResourceEntity resource;

    public LoanEntity() {
    }

    public LoanEntity(UUID id, Instant startDate, Instant dueDate, PendingState pendingState, UserEntity user, BibliographyResourceEntity resource) {
        this.id = id;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.pendingState = pendingState;
        this.user = user;
        this.resource = resource;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public PendingState getPendingState() {
        return pendingState;
    }

    public void setPendingState(PendingState pendingState) {
        this.pendingState = pendingState;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BibliographyResourceEntity getResource() {
        return resource;
    }

    public void setResource(BibliographyResourceEntity resource) {
        this.resource = resource;
    }
}