package interfaces;

import java.util.List;

public interface Pageable<T> {

    List<T> getPage(int limit, int offset);
}