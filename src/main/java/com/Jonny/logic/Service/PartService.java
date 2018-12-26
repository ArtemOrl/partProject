package com.Jonny.logic.Service;

import com.Jonny.logic.Entity.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartService {
    Part getPartById(Integer id);
    void savePart(Part part);
    void updatePart(Integer id, String name, boolean need, int quantity);
    void deletePart(Integer id);
    Page<Part> findAllByOrderByNeedAsc(Pageable pageable);
    Page<Part> findAllByOrderByNeedDesc(Pageable pageable);
    Page<Part> findAllByOrderByIdAsc(Pageable pageable);
    Page<Part> findAllByPage(Pageable pageable);

    Page<Part> search(String term, Pageable pageable);

    Page<Part> findAll(Pageable pageable);
    List<Part> findAll();

}
