package com.ruben.sigebi.infrastructure.persistence.entity.loan;

import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.ResourceCopyEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // ✅ El préstamo es sobre una copia específica, no sobre el recurso padre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_copy_id")
    private ResourceCopyEntity resourceCopy;

    public LoanEntity() {
    }

    public LoanEntity(UUID id, Instant startDate, Instant dueDate,
                      PendingState pendingState, Status status,
                      UserEntity user, ResourceCopyEntity resourceCopy) {
        this.id = id;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.pendingState = pendingState;
        this.status = status;
        this.user = user;
        this.resourceCopy = resourceCopy;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Instant getStartDate() { return startDate; }
    public void setStartDate(Instant startDate) { this.startDate = startDate; }

    public Instant getDueDate() { return dueDate; }
    public void setDueDate(Instant dueDate) { this.dueDate = dueDate; }

    public PendingState getPendingState() { return pendingState; }
    public void setPendingState(PendingState pendingState) { this.pendingState = pendingState; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public ResourceCopyEntity getResourceCopy() { return resourceCopy; }
    public void setResourceCopy(ResourceCopyEntity resourceCopy) { this.resourceCopy = resourceCopy; }
}