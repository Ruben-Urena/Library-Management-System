package com.ruben.sigebi.infrastructure.persistence.repository.ResourceCopyRepo;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.ResourceCopyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataResourceCopyRepository extends JpaRepository<ResourceCopyEntity, UUID> {
    List<ResourceCopyEntity> findByPhysicalResourceId(UUID physicalResourceId);
    List<ResourceCopyEntity> findByPhysicalResourceIdAndStatus(
            UUID physicalResourceId,
            Status status
    );

    List<ResourceCopyEntity> findByPhysicalResourceIdAndStatusAndState(
            UUID physicalResourceId,
            Status status,
            ResourceState state
    );
    Optional<ResourceCopyEntity> findFirstByPhysicalResourceIdAndStatusAndState(
            UUID physicalResourceId,
            Status status,
            ResourceState state
    );


    @Query("SELECT COUNT(rc) FROM ResourceCopyEntity rc " +
            "WHERE rc.physicalResource.id = :resourceId " +
            "AND rc.status = 'ACTIVE' " +
            "AND rc.state = 'AVAILABLE'")
    long countAvailableCopies(@Param("resourceId") UUID resourceId);


    @Query("SELECT COUNT(rc) FROM ResourceCopyEntity rc " +
            "WHERE rc.physicalResource.id = :resourceId " +
            "AND rc.status = 'ACTIVE' AND rc.state = :state")

    long countByResourceIdAndState(@Param("resourceId") UUID resourceId, @Param("state") ResourceState state);

    boolean existsByIdAndStatusAndState(UUID id, Status status, ResourceState state);
}
