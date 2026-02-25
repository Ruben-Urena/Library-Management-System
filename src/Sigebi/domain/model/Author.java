package Sigebi.domain.model;
import Sigebi.domain.common.exception.InvalidFullNameException;
import Sigebi.domain.common.objectValue.FullName;
import Sigebi.domain.valueObject.resource.AuthorId;
import Sigebi.domain.valueObject.resource.ResourceMainData;

import java.util.UUID;

public class Author {
    private FullName fullName;
    private final AuthorId authorId;
    public Author(FullName fullName) {
        notFullNameNull();
        this.authorId = new AuthorId(UUID.randomUUID());
    }
    private void notFullNameNull(){
        if (fullName == null){
            throw new InvalidFullNameException("Full name cannot be null.");
        }
    }
    public FullName getFullName() {
        return fullName;
    }
    public void changeName(FullName newFullname){
        notFullNameNull();
        this.fullName = newFullname;
    }
    public AuthorId getAuthorId() {
        return authorId;
    }

    public boolean isAuthorOf(ResourceMainData resourceMainData){
        return resourceMainData.author().authorId.value().equals(this.authorId.value());
    }

}
