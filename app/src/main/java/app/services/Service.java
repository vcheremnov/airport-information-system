package app.services;

import app.model.Entity;
import app.services.filters.Filter;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

import java.util.Collection;
import java.util.List;

public interface Service<E extends Entity> {

    ServiceResponse<Long> countAll();

    ServiceResponse<E> getById(Long id);

    ServiceResponse<Page<E>> getAll(PageInfo pageInfo);

    ServiceResponse<Page<E>> search(Filter<E> filter, PageInfo pageInfo);

    ServiceResponse<List<E>> getAllById(Collection<Long> idCollection);

    ServiceResponse<E> create(E entity);

    ServiceResponse<E> save(Long id, E entity);

    ServiceResponse<List<E>> saveAll(Collection<E> entityCollection);

    ServiceResponse<Void> deleteById(Long id);

    ServiceResponse<Void> deleteAllById(Collection<Long> idCollection);

}
