package Sigebi.domain.user.entities;
import Sigebi.domain.loan.Loan;
import Sigebi.domain.user.enums.UserStatus;

import java.util.List;

public abstract class Consumer extends User {
    private UserStatus userStatus;
    private List<Loan> loanList;
}
