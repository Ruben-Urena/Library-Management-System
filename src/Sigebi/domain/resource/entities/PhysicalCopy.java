package Sigebi.domain.resource.entities;
import Sigebi.domain.resource.enums.ResourceStatus;
import Sigebi.domain.resource.interfaces.Loanable;

public class PhysicalCopy  implements Loanable {
    private final String physicalFormat;
    private final String copyId;
    private ResourceStatus resourceStatus;
    private final PhysicalResource resource;
    private String shelfLocation;

    public PhysicalCopy(String physicalFormat,
                        String copyId,
                        ResourceStatus resourceStatus,
                        PhysicalResource resource,
                        String shelfLocation
                            ) {
        this.physicalFormat = physicalFormat;
        this.copyId = copyId;
        this.resourceStatus = resourceStatus;
        this.resource = resource;
        this.shelfLocation = shelfLocation;
    }


    public String getPhysicalFormat() {
        return physicalFormat;
    }

    public String getCopyId() {
        return copyId;
    }

    public ResourceStatus getResourceStatus() {
        return resourceStatus;
    }

    public BibliographicResource getResource() {
        return resource;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setResourceStatus(ResourceStatus resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }


    @Override
    public boolean canBeLoaned() {
        return false;
    }

    @Override
    public String calculateDueDate() {
        return "";
    }
}
