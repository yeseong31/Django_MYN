package org.project.myn.repository;

import org.project.myn.entity.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    // 사용자 이메일(아이디) 조회
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial = :social and m.email = :email")
    Optional<ClubMember> findByEmail(@Param("email") String email, @Param("social") boolean social);

}
