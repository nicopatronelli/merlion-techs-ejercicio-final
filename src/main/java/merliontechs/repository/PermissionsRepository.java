package merliontechs.repository;

import merliontechs.domain.Permissions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Permissions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
}
