package Sigebi.domain.model;
import Sigebi.domain.enums.ResourceStatus;
import Sigebi.domain.exeption.InvalidStateException;
import Sigebi.domain.interfaces.Loanable;
import Sigebi.domain.valueObject.resource.*;

import java.util.UUID;

public class PhysicalResource implements Loanable {
    private final PhysicalResourceID ID;
    private ResourceStatus status;
    private ResourceMainData mainData;
    private PublicationData publicationData;
    private PhysicalData physicalData;
    private CreditsData creditsData;
    private Language language;


    public PhysicalResource(ResourceMainData mainData, Language language){
        this.ID = new PhysicalResourceID(UUID.randomUUID());
        this.language = language;
        this.status = ResourceStatus.INACTIVE;
    }
    public PhysicalResource(ResourceMainData mainData, Language language, ResourceStatus resourceStatus){
        this.ID = new PhysicalResourceID(UUID.randomUUID());
        this.language = language;
        this.status = resourceStatus;
    }


    public PhysicalResourceID getID() {
        return ID;
    }

    public ResourceStatus getStatus() {
        return status;
    }

    public ResourceMainData getMainData() {
        return mainData;
    }

    public PublicationData getPublicationData() {
        return publicationData;
    }

    public PhysicalData getPhysicalData() {
        return physicalData;
    }

    public CreditsData getCreditsData() {
        return creditsData;
    }

    public Language getLanguage() {
        return language;
    }

    public void setPhysicalData(PhysicalData physicalData) {
        this.physicalData = physicalData;
    }

    public void publish(){
        if (this.status.equals(ResourceStatus.AVAILABLE) || this.status.equals(ResourceStatus.LOANED )){
            throw new InvalidStateException("Cannot publish a resource that is "+ this.status);
        }
    }

    public void conceal(){
        if (this.status.equals(ResourceStatus.LOANED)){
            throw new InvalidStateException("Cannot conceal a resource that is "+ this.status);
        }
        this.status = ResourceStatus.INACTIVE;
    }
    public void repair(){
        if (this.status.equals(ResourceStatus.AVAILABLE) ||this.status.equals(ResourceStatus.INACTIVE)){
            this.status = ResourceStatus.UNDER_REPAIR;
        }
        throw new InvalidStateException("Cannot repair a resource that is "+ this.status);

    }
    public void markAsLoaned(){
        if (this.status.equals(ResourceStatus.UNDER_REPAIR)||
                this.status.equals( ResourceStatus.INACTIVE) ||
                this.status.equals( ResourceStatus.LOST) ||
                this.status.equals(ResourceStatus.WITHDRAWN)){
            throw new InvalidStateException("Cannot loan a resource that is "+ this.status);
        }
        this.status = ResourceStatus.LOANED;
    }

    public void reserved(){
        if (!this.status.equals(ResourceStatus.AVAILABLE)){
            throw new InvalidStateException("Cannot reserve a resource that is "+ this.status);
        }
        this.status = ResourceStatus.RESERVED;
    }
    public void reportLost(){
        this.status = ResourceStatus.LOST;
    }
    public void withdrawn(){
        this.status = ResourceStatus.WITHDRAWN;
    }

    @Override
    public boolean canBeLoaned() {
        return !this.status.equals(ResourceStatus.LOANED) &&
                !this.status.equals(ResourceStatus.LOST) &&
                !this.status.equals(ResourceStatus.INACTIVE) &&
                !this.status.equals(ResourceStatus.RESERVED) &&
                !this.status.equals(ResourceStatus.WITHDRAWN) &&
                !this.status.equals(ResourceStatus.UNDER_REPAIR);
    }

}
