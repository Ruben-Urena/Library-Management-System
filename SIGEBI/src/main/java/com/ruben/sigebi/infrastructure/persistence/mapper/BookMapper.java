package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.author.repository.AuthorRepository;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import com.ruben.sigebi.infrastructure.persistence.entity.author.AuthorEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BookEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.embed.*;

import java.util.*;
import java.util.stream.Collectors;

import com.ruben.sigebi.infrastructure.persistence.entity.contributors.Contributors;
import com.ruben.sigebi.infrastructure.persistence.entity.publisher.Publisher;
import com.ruben.sigebi.infrastructure.persistence.entity.subject.Subject;
import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.FullNameEmbeddable;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookMapper() {
    }

    public BookEntity toEntity(Book book, Set<AuthorEntity> authors) {
        BookEntity bookEntity = new BookEntity();
        //status
        bookEntity.setStatus(book.getStatus());
        //ISBN
        bookEntity.setIsbn(new ISBNEmbeddable(book.getISBN().value()));
        //id
        bookEntity.setId(book.getId().value());
        //description
        if (book.getContentData() != null && book.getContentData().description() != null) {
            bookEntity.setDescription(book.getContentData().description());
        }
        //edition
        if (book.getEdition() != null) {
            bookEntity.setEdition(book.getEdition());
        }
        //title and subtitle
        if (book.getMainData().subtitle() != null) {
            bookEntity.setMainData(new ResourceMainDataEmbeddable(
                    book.getMainData().title(),
                    book.getMainData().subtitle()
            ));
        }else {
            bookEntity.setMainData(new ResourceMainDataEmbeddable(
                    book.getMainData().title(),
                    null
            ));
        }
        //Language
        bookEntity.setLanguage(new LanguageEmbeddable(book.getLanguage().toString()));

        //publication Date
        if (book.getPublicationData() != null) {
            bookEntity.setPublicationDate(book.getPublicationData().date());
        }
        //Physical data
        String _physicalFormat = null;
        String _shelfLocation = null;

        if (book.getPhysicalData() != null &&
                book.getPhysicalData().physicalFormat() != null &&
                book.getPhysicalData().shelfLocation() != null
        ) {
            _physicalFormat = book.getPhysicalData().physicalFormat();
            _shelfLocation = book.getPhysicalData().shelfLocation();

        } else if (
                book.getPhysicalData() != null &&
                        book.getPhysicalData().physicalFormat() == null &&
                        book.getPhysicalData().shelfLocation() != null
        ) {
            _shelfLocation = book.getPhysicalData().shelfLocation();
        } else if (
                book.getPhysicalData() != null &&
                        book.getPhysicalData().physicalFormat() != null &&
                        book.getPhysicalData().shelfLocation() == null
        ) {
            _physicalFormat = book.getPhysicalData().physicalFormat();
        }


        bookEntity.setPhysicalData(new PhysicalDataEmbeddable(
                _physicalFormat,
                _shelfLocation
        ));

        //resource type
        bookEntity.setResourceType(book.getResourceType());

        //subjects
        var subjectStr  = book.getContentData().subjectsList();

        Set<Subject> subjects = new HashSet<>();
        if (subjectStr != null && !subjectStr.isEmpty()) {
            for (var x : subjectStr){
                var newSub = new Subject();
                newSub.setSubjectName(x);
                subjects.add(newSub);
            }
        }
        bookEntity.setSubjectsList(subjects);


        //Authors
        bookEntity.setAuthorsIds(authors);


        //contributors
        if (book.getCreditsData().contributors() != null && !(book.getCreditsData().contributors().isEmpty()) ) {
            var contributorsStr  = book.getCreditsData().contributors();
            Set<Contributors> contributors = new HashSet<>();

            for (var x : contributorsStr){
                var newCon = new Contributors();
                newCon.setFullNameEmbeddable(new FullNameEmbeddable(x.name(),x.lastName()));
                contributors.add(newCon);
            }
            bookEntity.setContributors(contributors);
        }


       //publisher
        if(book.getCreditsData().publisher() != null && !(book.getCreditsData().publisher().isEmpty()) ) {
            var publishersStr  = book.getCreditsData().publisher();
            Set<Publisher> publishers = new HashSet<>();

            for (var x : publishersStr){
                var newPubl = new Publisher();
                newPubl.setFullNameEmbeddable(new FullNameEmbeddable(x, ""));
                publishers.add(newPubl);
            }
            bookEntity.setPublishers(publishers);
        }

        return bookEntity;
    }

    public Book toDomain(BookEntity entity) {
        System.out.println(entity.getAuthors()+" AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        Set<AuthorId> authors = entity.getAuthors()
                .stream()
                .map(a -> new AuthorId(UUID.fromString(a.getId().toString())))
                .collect(Collectors.toSet());

        var mainData = new ResourceMainData(entity.getMainData().getTitle(), entity.getMainData().getSubtitle());
        var language = new Language(entity.getLanguage().getLanguage().toLowerCase().trim());
        var id = new ResourceID(entity.getId());

        var book = new Book(id,language,null,mainData,null,entity.getResourceType(),entity.getEdition(),null,null,new ISBN(entity.getIsbn().getValue()));

        book.setStatus(entity.getStatus());

        //Content data
        var subjectList = entity.getSubjectsList();
        List<String> subjectListSTR =  new ArrayList<>();
        if (entity.getSubjectsList() != null) {
            for (var x: subjectList){
                if (x != null){
                    subjectListSTR.add(x.getSubjectName());
                }}
        }
        book.setContentData(new ContentData(
                entity.getDescription(),
                subjectListSTR
        ));


        //Credits data
        var contributors  =  entity.getContributors();
        var publishers =  entity.getPublishers();
        Set<FullName> contributorsSTR = new HashSet<>();
        Set<String> publisherSTR = new HashSet<>();

        if (  contributors != null &&  !(contributors.isEmpty())) {
            for (var x: contributors){
                if (x != null){
                    contributorsSTR.add(new FullName(x.getFullNameEmbeddable().getName(),x.getFullNameEmbeddable().getLastname()));

                }
            }
        }else {
            contributorsSTR = new HashSet<>();
        }

        if ( publishers != null &&  !(publishers.isEmpty())) {
            for (var x: publishers){
                if (x != null){
                    publisherSTR.add(x.getFullNameEmbeddable().getName()+x.getFullNameEmbeddable().getLastname());
                }
            }
        }else {
            publisherSTR = new  HashSet<>();
        }
        book.setCreditsData(new CreditsData(authors,contributorsSTR,publisherSTR));



        //publication date
        if (entity.getPublicationDate() != null) {
            book.setPublicationData(new PublicationData(entity.getPublicationDate()));
        }

        //Physical data
        String _physicalFormat = null;
        if (entity.getPhysicalData().getPhysicalFormat() != null) {
            _physicalFormat = entity.getPhysicalData().getPhysicalFormat();
        }
        book.setPhysicalData(new PhysicalData(
                _physicalFormat,
                entity.getPhysicalData().getShelfLocation()
        ));


        return  book;
    }
}