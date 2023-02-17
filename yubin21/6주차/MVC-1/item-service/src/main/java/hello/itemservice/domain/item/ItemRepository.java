package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // 싱글톤 생성
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static 주의. 동시성이 문제가 되는 상황이 발생할 수 있으므로, 실무에서는 HashMap 대신 ConcurrentHashMap을 사용할 것. Long도 atomic 등을 사용.
    private static long sequence = 0L; // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 프로젝트가 커질 경우, 설게상 새로운 class(ex. itemDto)를 만들어 관리하는 것이 좋다.
    // 중복 vs 명확성의 상황에서는 꼭 명확성을 지키는 것이 중요하다.
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
