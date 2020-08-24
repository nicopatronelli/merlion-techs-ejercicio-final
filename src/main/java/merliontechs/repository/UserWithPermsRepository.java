package merliontechs.repository;

import merliontechs.domain.UserWithPerms;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserWithPerms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserWithPermsRepository extends JpaRepository<UserWithPerms, Long> {

    @Query(value =
        "SELECT per.perm AS permission " +
        "FROM jhi_user u " +
            "JOIN user_with_perms uwp " +
                "ON u.id = uwp.jhi_user " +
            "JOIN permissions per " +
                "ON uwp.id = per.user_with_perms_id " +
        "WHERE u.login = :userName " +
        "GROUP BY per.perm",
        nativeQuery = true
    )
    List<String> findUserPermissionsByUserName(@Param("userName") String userName);
}
