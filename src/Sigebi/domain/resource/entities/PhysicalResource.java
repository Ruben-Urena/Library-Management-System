package Sigebi.domain.resource.entities;
import Sigebi.domain.resource.enums.IdentifierType;
import java.time.LocalDate;
import java.util.List;

public abstract class PhysicalResource extends BibliographicResource{
    public PhysicalResource(Long id, String title, List<String> authors, LocalDate publicationDate, String classification, IdentifierType identifierType) {
        super(id, title, authors, publicationDate, classification, identifierType);
    }
}
