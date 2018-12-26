package com.Jonny.logic.Service;

import com.Jonny.logic.Entity.Part;
import com.Jonny.logic.Repository.PartRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PartServiceImpl implements PartService {

    private PartRepository repository;

    @Autowired
    public void setProductRepository(PartRepository repository){this.repository = repository;}

    @Override
    @Transactional(readOnly = true)
    public Page<Part> search(String term, Pageable pageable) {
        return repository.findBySearchParams(term, pageable);
    }

    @Override
    public Page<Part> findAllByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Part getPartById(Integer id){ return repository.findOne(id);}

    @Override
    public void savePart(Part part) {
        repository.save(part);
    }

    @Override
    public void updatePart(Integer id, String name, boolean need, int quantity) {
        Part updated = repository.findOne(id);
        updated.setName(name);
        updated.setNeed(need);
        updated.setQuantity(quantity);
        repository.save(updated);
    }

    @Override
    public void deletePart(Integer id) {
        repository.delete(id);
    }

    @Override
    @Transactional(readOnly=true)
    public List<Part> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Page<Part> findAllByOrderByNeedAsc(Pageable pageable) {
        return repository.findAllByOrderByNeedAsc(pageable);
    }

    @Override
    public Page<Part> findAllByOrderByNeedDesc(Pageable pageable) {
        return repository.findAllByOrderByNeedDesc(pageable);
    }

    @Override
    public Page<Part> findAllByOrderByIdAsc(Pageable pageable) {
        return repository.findAllByOrderByIdAsc(pageable);
    }

    @Override
    public Page<Part> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


}
