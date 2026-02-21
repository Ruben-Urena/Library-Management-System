package Sigebi.domain.resource.interfaces;

import java.util.Date;

public interface Loanable {
    boolean canBeLoaned();
    String calculateDueDate();
}
