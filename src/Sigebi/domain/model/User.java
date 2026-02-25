package Sigebi.domain.model;

import Sigebi.domain.common.objectValue.FullName;
import Sigebi.domain.enums.Role;
import Sigebi.domain.enums.UserStatus;
abstract public class User {
    private Long id;
    private FullName fullName;
    private String email;
    private String password;
    private Role role;
    private UserStatus status;
    private int loanLimit;
}
