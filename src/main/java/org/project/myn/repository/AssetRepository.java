package org.project.myn.repository;

import org.project.myn.entity.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    // Asset 정보와 Account 정보를 함께 조회
    @Query("select a, ac from Asset a, Account ac where a.id = :id and a.account.id = ac.id")
    List<Object[]> getAssetWithAccount(@Param("id") Long id);

    // '/explore'의 'New Assets'에 해당하는 정보: 가장 최근에 등록된 Asset 정보와 username 정보 조회
    @Query("select a, ac.username from Asset a left outer join Account ac on ac.id = a.account.id")
    Page<Object[]> getNewAssets(Pageable pageable);

}
