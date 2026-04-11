package com.ruben.sigebi.domain.bibliographyResource.entity;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import java.util.Set;


public class Book extends PhysicalResource {
    private final ISBN isbn;


    public Book(ResourceMainData mainData, Language language, String resourceType,
                Set<AuthorId> authorId, ResourceID resourceID, PhysicalData physicalData, ISBN isbn) {
        super(mainData, language, resourceType, authorId, resourceID, physicalData);
        this.isbn = isbn;
        activate();
    }


    public Book(ResourceID resourceID, Language language, CreditsData creditsData,
                ResourceMainData mainData, PublicationData publicationData, String resourceType,
                String edition, ContentData contentData, PhysicalData physicalData, ISBN isbn) {
        super(resourceID, language, creditsData, mainData, publicationData, resourceType, edition, contentData, physicalData);
        this.isbn = isbn;
    }

    public ISBN getISBN() {
        return isbn;
    }

    @Override
    public String universalIdentifier() {
        return isbn.value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getISBN().equals(book.getISBN()) && this.getId().equals(book.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode() + getISBN().hashCode();
    }
}
