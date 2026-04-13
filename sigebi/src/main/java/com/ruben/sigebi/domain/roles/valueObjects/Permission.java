package com.ruben.sigebi.domain.roles.valueObjects;
import com.ruben.sigebi.domain.common.exception.InvalidationException;

import java.util.Objects;

public record Permission (String source, String action){
    public Permission  {
        Objects.requireNonNull(source);
        Objects.requireNonNull(action);
        if (source.isBlank() || action.isBlank()) {
            throw  new InvalidationException("source or action cannot be blank");
        }
    }
    public String value(){
        return source + ":" + action;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;
        return source().equals(that.source()) && action().equals(that.action());
    }

    @Override
    public int hashCode() {
        int result = source().hashCode();
        result = 31 * result + action().hashCode();
        return result;
    }
}
