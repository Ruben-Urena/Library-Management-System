package com.ruben.sigebi.infrastructure.persistence.entity.publisher;

import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.FullNameEmbeddable;
import jakarta.persistence.*;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    private FullNameEmbeddable fullNameEmbeddable;

    public Publisher(String id, FullNameEmbeddable fullNameEmbeddable) {
        this.id = id;
        this.fullNameEmbeddable = fullNameEmbeddable;
    }

    public Publisher() {}

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

