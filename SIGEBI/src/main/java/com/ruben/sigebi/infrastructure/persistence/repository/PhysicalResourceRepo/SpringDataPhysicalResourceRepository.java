package com.ruben.sigebi.infrastructure.persistence.repository.PhysicalResourceRepo;

import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.PhysicalResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPhysicalResourceRepository extends JpaRepository<PhysicalResourceEntity, UUID> {

}