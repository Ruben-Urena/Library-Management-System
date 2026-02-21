package Sigebi.domain.resource.entities;
import Sigebi.domain.resource.enums.IdentifierType;
import java.time.LocalDate;
import java.util.List;
public class NormalBook extends PhysicalResource{
    public NormalBook(Long id, String title, List<String> authors, LocalDate publicationDate, String classification, IdentifierType identifierType) {
        super(id, title, authors, publicationDate, classification, identifierType);
    }
}
