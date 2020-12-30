package cz.cvut.bikesharingproject.service;

import cz.cvut.bikesharingproject.dao.BaseDao;
import cz.cvut.bikesharingproject.model.AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

public abstract class BaseService<T extends AbstractEntity> {

    protected abstract BaseDao<T> getBaseDao();

    @Transactional
    public void persist(T entity) {
        Objects.requireNonNull(entity);
        getBaseDao().persist(entity);
    }

    @Transactional
    public T find(Integer id) {
        Objects.requireNonNull(id);
        return getBaseDao().find(id);
    }

    @Transactional
    public List<T> findAll() {
        return getBaseDao().findAll();
    }

    @Transactional
    public void update(T entity) {
        Objects.requireNonNull(entity);
        getBaseDao().update(entity);
    }

    @Transactional
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        entity.setRemoved(true);
        getBaseDao().update(entity);
    }
}
