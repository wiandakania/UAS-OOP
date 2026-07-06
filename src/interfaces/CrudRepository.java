package interfaces;

import java.util.List;

public interface CrudRepository<T, ID> {

    int create(T entity);

    int update(T entity);

    int delete(ID id);

    T search(ID id);

    List<T> getAll();

    int count();
}