package com.ruben.sigebi.infrastructure.persistence.entity.publisher;

import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.FullNameEmbeddable;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private FullNameEmbeddable fullNameEmbeddable;

    public Publisher(UUID id, FullNameEmbeddable fullNameEmbeddable) {
        this.id = id;
        this.fullNameEmbeddable = fullNameEmbeddable;
    }

    public Publisher() {}

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

