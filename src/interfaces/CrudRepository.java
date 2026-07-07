package interfaces;

public interface CrudRepository<T, ID> {

    int create(T entity);

    int update(T entity);

    int delete(ID id);
}