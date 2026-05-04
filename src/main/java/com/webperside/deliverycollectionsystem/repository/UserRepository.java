package com.webperside.deliverycollectionsystem.repository;


import com.webperside.deliverycollectionsystem.model.dto.RoleDto;
import com.webperside.deliverycollectionsystem.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Page<User> findByIsDeletedFalse(Pageable pageable);

    @Query("""
    SELECT new com.webperside.deliverycollectionsystem.model.dto.RoleDto(r.name)
    FROM User u
    JOIN u.roles r
    WHERE u.email = :email
""")
    List<RoleDto> findRolesByEmail(@Param("email") String email);

    List<User> findAllByRoles_Name(String name);
}
