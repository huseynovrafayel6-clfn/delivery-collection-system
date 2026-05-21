package com.webperside.deliverycollectionsystem.repository;

import com.webperside.deliverycollectionsystem.model.entity.Return;
import com.webperside.deliverycollectionsystem.model.enums.returns.ReturnReason;
import com.webperside.deliverycollectionsystem.model.enums.returns.ReturnStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {

    Page<Return> findByIsDeletedFalse(Pageable pageable);

    long countByStatus(ReturnStatus status);

    long countByReason(ReturnReason reason);

}
