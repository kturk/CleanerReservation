package com.justlife.casestudy.service;

import com.justlife.casestudy.repository.ModelRepository;

import java.io.Serializable;
import java.util.List;

public class ModelService<T, ID extends Serializable> {

    public String resourceName;

    private ModelRepository<T, ID> repository;

    public ModelService(String resourceName, ModelRepository<T, ID> repository) {
        this.resourceName = resourceName;
        this.repository = repository;
    }

//    public List<T> listAll () {
//        List<T> returnList = new ArrayList<>();
//        repository.findAll().forEach(returnList::add);
//        return returnList;
//    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(ID id) {
        return (T) repository.findById(id);
    }

    public <S extends T> S save(S s) {
        return repository.save(s);
    }

    public <S extends T> List<S> saveAll(Iterable<S> iterable) {
        return repository.saveAll(iterable);
    }

    public boolean deleteById(ID id) {
        repository.deleteById(id);
        return true;
    }

    public boolean delete(T t) {
        repository.delete(t);
        return true;
    }
}
