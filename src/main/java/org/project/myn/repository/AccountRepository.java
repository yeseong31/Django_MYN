package org.project.myn.repository;

import org.project.myn.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // 해당 아이디(id)를 가진 사용자 정보 조회
    @EntityGraph(attributePaths = {"clubMember"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select ac from Account ac where ac.id = :id")
    Optional<Account> findAccountWithEmail(@Param("id") Long id);

    // 해당 username을 가진 모든 사용자 정보 조회
    @EntityGraph(attributePaths = {"clubMember"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select ac from Account ac where ac.username = :username")
    List<Account> getList(@Param("username") String username);

}