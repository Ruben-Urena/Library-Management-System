package com.ruben.sigebi.domain.author.entity;
import com.ruben.sigebi.domain.author.valueObjects.Country;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import com.ruben.sigebi.domain.author.valueObjects.AuthorId;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public final class Author extends ActivatableAggregate {

    private final FullName fullName;
    private final AuthorId authorId;
    private final Date birthdate;
    private final Country country;
    private String masterpiece;

    public Author(AuthorId authorId, FullName fullName, Date birthdate, Country country) {
        this.fullName =Objects.requireNonNull(fullName, "Author's full name cannot be null: "+ fullName);
        this.authorId = Objects.requireNonNull(authorId, "Author's id cannot be null: "+ authorId);
        this.birthdate = Objects.requireNonNull(birthdate, "Author's birthdate cannot be null: "+ birthdate);
        this.country = Objects.requireNonNull(country,"Author's country cannot be null"+ country);
    }


    public FullName getFullName() {
        return fullName;
    }

    public AuthorId getAuthorId() {
        return authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;
        return getAuthorId().equals(author.getAuthorId());
    }

    @Override
    public int hashCode() {
        return getAuthorId().hashCode();
    }
}
