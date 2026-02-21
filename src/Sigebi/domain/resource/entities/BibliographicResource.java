package Sigebi.domain.resource.entities;
import Sigebi.domain.resource.enums.IdentifierType;
import java.time.LocalDate;
import java.util.List;

public abstract class BibliographicResource  {
     private final Long id;
     private final String title;
     private String subtitle;
     private final List<String> authors;
     private List<String> contributors;
     private  String publisher;
     private final LocalDate publicationDate;
     private  String edition;
     private  String language;
     private String description;
     private List<String> subjects;
     private final String classification;
     private final IdentifierType identifierType;

     public BibliographicResource(Long id, String title, List<String> authors, LocalDate publicationDate, String classification, IdentifierType identifierType) {
          this.id = id;
          this.title = title;
          this.authors = authors;
          this.publicationDate = publicationDate;
          this.classification = classification;
          this.identifierType = identifierType;
     }


     public Long getId() {
          return id;
     }

     public String getTitle() {
          return title;
     }

     public String getSubtitle() {
          return subtitle;
     }

     public void setSubtitle(String subtitle) {
          this.subtitle = subtitle;
     }

     public List<String> getAuthors() {
          return authors;
     }

     public List<String> getContributors() {
          return contributors;
     }

     public void setContributors(List<String> contributors) {
          this.contributors = contributors;
     }

     public String getPublisher() {
          return publisher;
     }

     public void setPublisher(String publisher) {
          this.publisher = publisher;
     }

     public LocalDate getPublicationDate() {
          return publicationDate;
     }

     public String getEdition() {
          return edition;
     }

     public void setEdition(String edition) {
          this.edition = edition;
     }

     public String getLanguage() {
          return language;
     }

     public void setLanguage(String language) {
          this.language = language;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }

     public List<String> getSubjects() {
          return subjects;
     }

     public void setSubjects(List<String> subjects) {
          this.subjects = subjects;
     }

     public String getClassification() {
          return classification;
     }

     public IdentifierType getIdentifierType() {
          return identifierType;
     }



}


//Circulating/Short-loan/StandardLoanPolicy/In-library use only/vista-only
//
//Restricted/
//

//
//In-library use only/
//
//Licensed access (digital)