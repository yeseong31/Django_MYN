package org.project.myn.repository;

import org.project.myn.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    // 가장 최근에 등록된 Collection 정보 조회
    @Query("select co from Collection co")
    Page<Object[]> getNewCollections(Pageable pageable);

}
