package com.ruben.sigebi.infrastructure.persistence.repository.ResourceCopyRepo.adapter;

import com.ruben.sigebi.domain.bibliographyResource.entity.ResourceCopy;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.repository.ResourceCopyRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.PhysicalResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.mapper.ResourceCopyMapper;
import com.ruben.sigebi.infrastructure.persistence.repository.PhysicalResourceRepo.SpringDataPhysicalResourceRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.ResourceCopyRepo.SpringDataResourceCopyRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaResourceCopyRepository implements ResourceCopyRepository {
    private final SpringDataResourceCopyRepository springDataResourceCopyRepository;
    private final SpringDataPhysicalResourceRepository springDataPhysicalResourceRepository;

    public JpaResourceCopyRepository(SpringDataResourceCopyRepository springDataResourceCopyRepository, SpringDataPhysicalResourceRepository springDataPhysicalResourceRepository) {
        this.springDataResourceCopyRepository = springDataResourceCopyRepository;
        this.springDataPhysicalResourceRepository = springDataPhysicalResourceRepository;
    }

    @Override
    public void save(ResourceCopy copy) {
        PhysicalResourceEntity physicalResourceEntity = (PhysicalResourceEntity) springDataPhysicalResourceRepository
                .findById(copy.getPhysicalResourceId().value())
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                        "PhysicalResource not found with id: " + copy.getPhysicalResourceId().value()
                ));

        springDataResourceCopyRepository.save(ResourceCopyMapper.toEntity(copy, physicalResourceEntity));
    }

    @Override
    public Optional<ResourceCopy> findById(ResourceCopyId id) {
        return springDataResourceCopyRepository.findById(id.value())
                .map(ResourceCopyMapper::toDomain);
    }

    @Override
    public List<ResourceCopy> findByResourceId(ResourceID physicalResourceId) {
        return springDataResourceCopyRepository
                .findByPhysicalResourceId(physicalResourceId.value())
                .stream()
                .map(ResourceCopyMapper::toDomain)
                .toList();
    }

    @Override
    public List<ResourceCopy> findByResourceIdAndStatus(ResourceID physicalResourceId, Status status) {
        return springDataResourceCopyRepository
                .findByPhysicalResourceIdAndStatus(physicalResourceId.value(), status)
                .stream()
                .map(ResourceCopyMapper::toDomain)
                .toList();
    }

    @Override
    public List<ResourceCopy> findByResourceIdStatusAndState(ResourceID physicalResourceId, Status status, ResourceState state) {
        return springDataResourceCopyRepository
                .findByPhysicalResourceIdAndStatusAndState(physicalResourceId.value(), status, state)
                .stream()
                .map(ResourceCopyMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ResourceCopy> findFirstAvailable(ResourceID physicalResourceId) {
        return springDataResourceCopyRepository
                .findFirstByPhysicalResourceIdAndStatusAndState(
                        physicalResourceId.value(),
                        Status.ACTIVE,
                        ResourceState.AVAILABLE
                )
                .map(ResourceCopyMapper::toDomain);
    }

    @Override
    public long countAvailable(ResourceID physicalResourceId) {
        return springDataResourceCopyRepository.countAvailableCopies(
                physicalResourceId.value()
        );
    }

    @Override
    public long countByState(ResourceID physicalResourceId, ResourceState state) {
        return springDataResourceCopyRepository.countByResourceIdAndState(
                physicalResourceId.value(),
                state
        );
    }

    @Override
    public boolean isAvailable(ResourceCopyId copyId) {
        return springDataResourceCopyRepository.existsByIdAndStatusAndState(
                copyId.value(),
                Status.ACTIVE,
                ResourceState.AVAILABLE
        );
    }
}
