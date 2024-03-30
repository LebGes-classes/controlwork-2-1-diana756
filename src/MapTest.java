import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    @Test
    public void testMapOperations() {
        // Создание экземпляра Map (HashMap)
        Map<String, Integer> map = new HashMap<>();

        // Добавление элементов
        map.put("яблоко", 10);
        map.put("банан", 5);

        assertEquals(2, map.size());

        // Проверка получения значения по ключу
        assertEquals(Integer.valueOf(10), map.get("яблоко"));

        // Проверка наличия ключа
        assertTrue(map.containsKey("банан"));

        map.remove("яблоко");

        // Проверка отсутствия удаленного элемента
        assertNull(map.get("банан"));

        assertEquals(1, map.size());

        map.clear();

        assertTrue(map.isEmpty());
    }
}
