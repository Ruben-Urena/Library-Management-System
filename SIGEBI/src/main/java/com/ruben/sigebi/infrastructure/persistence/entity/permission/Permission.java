package com.ruben.sigebi.infrastructure.persistence.entity.permission;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String source;
    private String action;

    public Permission() {
    }

    public Permission(String source, String action) {
        this.source = source;
        this.action = action;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}