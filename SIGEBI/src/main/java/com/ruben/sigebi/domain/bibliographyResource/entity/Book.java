package com.ruben.sigebi.domain.bibliographyResource.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.events.NormalPhysicalResourceUpdate;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Loanable;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Reservable;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.InvalidStateException;
import com.ruben.sigebi.domain.common.exception.InvalidStatusException;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;


public class Book extends PhysicalResource implements Loanable , Reservable{
    private final ISBN ISBN;

    public Book(ResourceMainData mainData, Language language, String resourceType, Set<AuthorId> authorIdSet, ISBN isbn, Integer quantity) {
        super(mainData, language, resourceType, authorIdSet, quantity);
        this.ISBN = Objects.requireNonNull(isbn);
    }

    public Book(ResourceMainData mainData, Language language, String resourceType, Set<AuthorId> authorId,  ISBN ISBN, ResourceID resourceID) {
        super(mainData, language, resourceType, authorId, resourceID);
        this.ISBN = ISBN;
    }

    public ISBN getISBN() {
        return ISBN;
    }

    @Override
    public void markAsLoaned(UserId userId){
        if ( !((this.getState().equals(ResourceState.AVAILABLE) ||  this.getState().equals(ResourceState.RESERVED))) ){
            throw new InvalidStateException("Cannot loan a resource that state is "+ this.getState());
        }
        if (this.status.equals(Status.INACTIVE)){
            throw new InvalidStatusException("Cannot loan a resource that status is "+ this.status);
        }
        this.setState(ResourceState.LOANED) ;
    }
    //20

    @Override
    public boolean isLoaned() {
        return this.getState().equals(ResourceState.LOANED);
    }



    @Override
    public void markAsReserved(UserId userId){
        if ( !this.getState().equals(ResourceState.AVAILABLE) ){
            throw new InvalidStateException("Cannot reserve a resource that state is "+ this.getState());
        }
        if (this.status.equals(Status.INACTIVE)){
            throw new InvalidStatusException("Cannot reserve a resource that status is "+ this.status);
        }
        this.setState(ResourceState.RESERVED);
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getISBN().equals(book.getISBN())&& this.getId().equals(book.getId());
    }

    @Override
    public int hashCode() {
        var getISBN = getISBN().hashCode();
        var getId = getId().hashCode();
        return getId+getISBN;
    }

    @Override
    public String universalIdentifier() {
        return getISBN().value();
    }
}
