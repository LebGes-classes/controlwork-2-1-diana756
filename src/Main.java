public class Main {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();  //создание экземпляра
        map.put("яблоко",5);                         //добавление эл-в
        map.put("апельсин", 7);
        map.put("банан", 3);

        System.out.println("Количество яблок " + map.get("яблоко"));  //получение значения по ключу
        System.out.println("Количество апельсинов " + map.get("апельсин"));
        System.out.println("Количество бананов " + map.get("банан"));

        if (map.containsKey("апельсин")){                            //проверка наличия ключа
            System.out.println("У нас есть апельсины");
        }
        if (!map.containsKey("груша")) {
            System.out.println("У нас нет груш");
        }
        System.out.println("Размер таблицы - " + map.size());
        System.out.println("Удаление ключа яблоко");
        map.remove("яблоко");
        System.out.println("Размер таблицы - " + map.size());
        if (!map.containsKey("яблоко")) {
            System.out.println("У нас нет яблок");
        }
    }
}