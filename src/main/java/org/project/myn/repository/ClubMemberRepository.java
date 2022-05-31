package org.project.myn.repository;

import org.project.myn.entity.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    // 사용자 이메일(아이디)과 소셜가입여부를 확인하여 정보 조회
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial = :social and m.email = :email")
    Optional<ClubMember> findByEmailWithSocial(@Param("email") String email, @Param("social") boolean social);

    // 사용자 이메일을 확인하여 정보 조회
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.email = :email")
    Optional<ClubMember> findByEmail(@Param("email") String email);

    // 사용자 id를 통해 정보 조회
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.id = :id")
    Optional<ClubMember> findById(@Param("id") Long id);

    // 사용자 id를 통해 사용자 삭제
    @Modifying
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("delete from ClubMember m where m.id = :id")
    void deleteById(@Param("id") Long id);
}
