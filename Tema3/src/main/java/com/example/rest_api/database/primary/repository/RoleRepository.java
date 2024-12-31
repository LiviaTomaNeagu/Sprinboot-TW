package com.example.rest_api.database.primary.repository;

import com.example.rest_api.database.primary.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    Boolean existsByName(String name);

    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
        FROM RoleEntity r
        JOIN r.permissions p
        WHERE r.name = :roleName AND p.method = :permission AND p.endpoint = :albumEndpoint
    """)
    boolean hasPermissionForRole(@Param("roleName") String roleName,
                                 @Param("permission") String permission,
                                 @Param("albumEndpoint") String albumEndpoint);


    @Query("""
    SELECT r
    FROM RoleEntity r
    JOIN r.users u
    WHERE u.id = :userId
    """)
    List<RoleEntity> findRolesByUserId(@Param("userId") Long userId);

}
