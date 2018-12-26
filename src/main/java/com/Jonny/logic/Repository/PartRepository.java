package com.Jonny.logic.Repository;

import com.Jonny.logic.Entity.Part;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;


public interface PartRepository extends PagingAndSortingRepository<Part, Integer> {

    @Query("SELECT t FROM Part t WHERE t.name LIKE CONCAT('%', :searchTerm, '%')")

    Page<Part> findBySearchParams(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    Page<Part> findAllByOrderByNeedAsc(Pageable pageable);
    Page<Part> findAllByOrderByNeedDesc(Pageable pageable);
    Page<Part> findAllByOrderByIdAsc(Pageable pageable);
}
