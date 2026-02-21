package Sigebi.domain.user.entities;

import Sigebi.domain.user.enums.Role;
import Sigebi.domain.user.enums.UserStatus;

abstract public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private UserStatus status;
    private int loanLimit;
}
