
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.author.repository.AuthorRepository;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.NormalPhysicalBibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.enums.PenaltyState;
import com.ruben.sigebi.domain.penalty.exception.InvalidPenaltyException;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.PostgresAuthorRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.PostgresPenaltyRepository;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.PostgresBibliographyRepository;


public class Main {
    public static void main(String[] args) {
        AuthorRepository authorRepo = new PostgresAuthorRepository();
        BibliographyRepository resourceRepo = new PostgresBibliographyRepository();
        PenaltyRepository penaltyRepo = new PostgresPenaltyRepository();

        try {
            System.out.println("🚀 INICIANDO PRUEBAS DE SISTEMA BIBLIOTECARIO...");

            // --- PASO 1: CREAR Y GUARDAR AUTOR ---
            System.out.println("\n1. Persistiendo Autor...");
            AuthorId authorId = new AuthorId(UUID.randomUUID());
            FullName fullName = new FullName("Gabriel", "García Márquez");
            Author author = new Author(authorId, fullName);

            authorRepo.save(author);
            System.out.println("✅ Autor guardado: " + fullName.name() + " " + fullName.lastName());

            // --- PASO 2: CREAR Y GUARDAR RECURSO (LIBRO) ---
            System.out.println("\n2. Persistiendo Recurso Bibliográfico...");
            ResourceID resourceId = new ResourceID(UUID.randomUUID());
            BibliographyResource libro = new NormalPhysicalBibliographyResource(
                    resourceId,
                    new Language("es"),
                    new ResourceMainData("Cien años de soledad",null,authorId,null ),
                    "hardcover",
                    null,
                    null
            );
            // ResourceID resourceID, Language language, ResourceMainData mainData, String resourceType, CreditsData creditsData, PublicationData publicationData)

            resourceRepo.save((NormalPhysicalBibliographyResource) libro);
            System.out.println("✅ Recurso guardado: " + libro.getMainData().title());

            // --- PASO 3: CREAR Y GUARDAR MULTA (PENALTY) ---
            System.out.println("\n3. Persistiendo Multa (Lógica de Negocio)...");
            PenaltyId pId = new PenaltyId(UUID.randomUUID());
            UserId uId = new UserId(UUID.randomUUID());

            Penalty penalty = new Penalty(pId,"SSS", uId, Instant.now(),Instant.now(), PenaltyState.ACTIVE);
            //(PenaltyId penaltyId, String description, UserId userId,
            //                   Instant startDate, Instant endDate, PenaltyState state) {
            penaltyRepo.save(penalty);
            System.out.println("✅ Multa guardada exitosamente.");
            System.out.println("   - Fecha inicio: " + penalty.getStartDate());
            System.out.println("   - Fecha fin (5 días después): " + penalty.getEndDate());

            // --- PASO 4: PRUEBA DE VALIDACIÓN DE DOMINIO ---
            System.out.println("\n4. Probando validación de días negativos...");
            try {
                new Penalty(new PenaltyId(UUID.randomUUID()), uId, 0);
            } catch (InvalidPenaltyException e) {
                System.out.println("✅ Capturada excepción esperada: " + e.getMessage());
            }

            System.out.println("\n--- ✨ TODAS LAS PRUEBAS COMPLETADAS CON ÉXITO ---");

        } catch (Exception e) {
            System.err.println("\n❌ ERROR DURANTE LA EJECUCIÓN:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

//public class Main {
//    public static void main(String[] args) {
//        var authorRepo = new PostgresAuthorRepository();
//        var biblioRepo = new PostgresBibliographyRepository();
//
//        AuthorId authorId = new AuthorId(UUID.randomUUID());
//        Author author = new Author(authorId, new FullName("Gabriel", "García Márquez"));
//
//        // 3. Guardar
//        authorRepo.save(author);
//
//        // 4. Crear Recurso Físico vinculado al Autor
//        ResourceID resId = new ResourceID(UUID.randomUUID());
//        ResourceMainData mainData = new ResourceMainData("Cien Años de Soledad", null, author.getAuthorId(), "1st");
//
//        NormalPhysicalBibliographyResource book = new NormalPhysicalBibliographyResource(
//                resId, new Language("ES"), mainData, "PHYSICAL", null, null
//        );
//
//        biblioRepo.save(book);
//
//        System.out.println("¡Proceso completado! Recurso guardado con éxito.");
//    }
//}
//public class Main {
//    public static void main(String[] args) {
//
//        UserRepository repo = new PostgresUserRepository();
//
//
//        User testUser = new User(
//                new UserId(UUID.randomUUID()),
//                new FullName("Ruben", "Dev"),
//                new EmailAddress("test@ruben.com"),
//                new Password("test@rubenA1"),
//                new HashSet<>(),
//                UserStatus.ACTIVE
//        );
//
//
//        try {
//            System.out.println("Intentando guardar usuario...");
//            repo.save(testUser);
//            System.out.println("¡Guardado con éxito!");
//
//            System.out.println("Buscando usuario guardado...");
//            repo.findById(testUser.getUserId()).ifPresent(u ->
//                    System.out.println("Encontrado: " + u.getFullName().name())
//            );
//
//        } catch (Exception e) {
//            System.err.println("ALGO FALLÓ:");
//            e.printStackTrace();
//        }
//    }
//}