package com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource;

import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.embed.ISBNEmbeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class BookEntity extends PhysicalResourceEntity {
    @Embedded
    private ISBNEmbeddable isbn;

    public BookEntity() {

    }

    public ISBNEmbeddable getIsbn() {
        return isbn;
    }

    public void setIsbn(ISBNEmbeddable isbn) {
        this.isbn = isbn;
    }
}