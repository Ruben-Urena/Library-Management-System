package com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ResourceMainDataEmbeddable {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    protected ResourceMainDataEmbeddable() {}

    public ResourceMainDataEmbeddable(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}