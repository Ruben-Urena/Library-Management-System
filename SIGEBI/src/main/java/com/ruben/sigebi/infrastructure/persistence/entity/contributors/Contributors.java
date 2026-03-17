package com.ruben.sigebi.infrastructure.persistence.entity.contributors;

import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.FullNameEmbeddable;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "contributors")
public class Contributors {
    @id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    private FullNameEmbeddable fullNameEmbeddable;

    public Contributors() {
    }

    public Contributors(String id, FullNameEmbeddable fullNameEmbeddable) {
        this.id = id;
        this.fullNameEmbeddable = fullNameEmbeddable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FullNameEmbeddable getFullNameEmbeddable() {
        return fullNameEmbeddable;
    }

    public void setFullNameEmbeddable(FullNameEmbeddable fullNameEmbeddable) {
        this.fullNameEmbeddable = fullNameEmbeddable;
    }
}
