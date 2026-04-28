package com.ruben.sigebi.domain.User.valueObject;

import com.ruben.sigebi.domain.common.objectValue.FullName;

import java.security.SecureRandom;

public class ReturnCode {
    private FullName fullName;
    private String title;
    private String code;

    public ReturnCode(FullName fullName, String title) {
        this.fullName = fullName;
        this.title = title;
        generateCode();
    }

    public ReturnCode(String code) {
        this.code = code;
    }

    public void generateCode() {
        String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        SecureRandom random = new SecureRandom();


        char userInitial = Character.toUpperCase(fullName.name().charAt(0));
        char resourceInitial = Character.toUpperCase(title.charAt(0));

        StringBuilder middle = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(CHARACTERS.length());
            middle.append(CHARACTERS.charAt(index));
        }
        this.code = "L-" + userInitial + middle + "-" + resourceInitial;

    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
