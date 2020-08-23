package merliontechs.repository;

import merliontechs.domain.UserWithPerms;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserWithPerms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserWithPermsRepository extends JpaRepository<UserWithPerms, Long> {
}
