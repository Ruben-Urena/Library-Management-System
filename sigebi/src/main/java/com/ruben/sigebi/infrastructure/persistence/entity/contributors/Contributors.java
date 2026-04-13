package com.ruben.sigebi.infrastructure.persistence.entity.contributors;

import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.FullNameEmbeddable;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "contributors")
public class Contributors {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private FullNameEmbeddable fullNameEmbeddable;

    public Contributors() {
    }

    public Contributors(UUID id, FullNameEmbeddable fullNameEmbeddable) {
        this.id = id;
        this.fullNameEmbeddable = fullNameEmbeddable;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FullNameEmbeddable getFullNameEmbeddable() {
        return fullNameEmbeddable;
    }

    public void setFullNameEmbeddable(FullNameEmbeddable fullNameEmbeddable) {
        this.fullNameEmbeddable = fullNameEmbeddable;
    }
}
