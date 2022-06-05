package org.project.myn.repository;

import org.project.myn.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // 해당 아이디(id)를 가진 사용자 정보 조회
    @EntityGraph(attributePaths = {"clubMember"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select ac from Account ac where ac.id = :id")
    Optional<Account> findAccountWithEmail(@Param("id") Long id);

    // 해당 이메일을 가진 사용자 정보 조회
    @Query("select ac from Account ac where ac.clubMember.id = (select m.id from ClubMember m where m.email = :email)")
    Optional<Account> findAccountByEmail(@Param("email") String email);

    // 해당 username을 가진 모든 사용자 정보 조회
    @EntityGraph(attributePaths = {"clubMember"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select ac from Account ac where ac.username = :username")
    List<Account> getList(@Param("username") String username);

    // 해당 id를 가진 사용자 삭제
    @Modifying
    @EntityGraph(attributePaths = {"clubMember"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("delete from Account ac where ac.clubMember.id = :id")
    void deleteByClubMemberId(@Param("id") Long id);

    // 해당 이메일을 가진 사용자 삭제
    @Modifying
    @Query("delete from Account ac where ac.clubMember.id = (select m.id from ClubMember m where m.email = :email)")
    void deleteByClubMemberEmail(@Param("email") String email);

}