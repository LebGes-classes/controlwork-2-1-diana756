public class HashMap<K, V> implements Map<K, V> {
    private final static int SIZE = 16;
    private final Node<K, V>[] table;
    private int size = 0;

    public HashMap() {
        this.table = (Node<K, V>[]) new Node[SIZE];
    }

    @Override
    public void put(K key, V value) {
        int hash = hashCode(key);
        int index = hash & (table.length - 1);
        Node<K, V> first = table[index];
        if (first == null) {
            table[index] = new Node<>(key, value, hash);
            size++;
        } else {
            Node<K, V> node = findNodeInBucket(first, hash, key);
            if (node.getHash() == hash && node.getKey().equals(key)) {
                node.setValue(value);
            } else {
                node.setNext(new Node<>(key, value, hash));
                size++;
            }
        }
    }

    @Override
    public V get(K key) {
        int hash = hashCode(key);
        int index = hash & (table.length - 1);
        Node<K, V> first = table[index];
        if (first != null) {
            Node<K, V> node = findNodeInBucket(first, hash, key);
            if (node.getHash() == hash && node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (Node<K, V> bucket : table) {
            Node<K, V> node = bucket;
            while (node != null) {
                if (node.getValue() != null && node.getValue().equals(value)) {
                    return true;
                }
                node = node.getNext();
            }
        }
        return false;
    }

    @Override
    public void remove(K key) {
        int hash = hashCode(key);
        int index = hash & (table.length - 1);
        Node<K, V> first = table[index];
        if (first != null) {
            if (first.getHash() == hash && first.getKey().equals(key)) {
                table[index] = first.getNext();
                size--;
            }
            Node<K, V> prevNode = first;
            Node<K, V> node = first.getNext();
            while (node != null && (node.getHash() != hash || !node.getKey().equals(key))) {
                prevNode = node;
                node = node.getNext();
            }
            if (node != null && node.getHash() == hash && node.getKey().equals(key)) {
                prevNode.setNext(node.getNext());
                size--;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }


    private Node<K, V> findNodeInBucket(Node<K, V> first, int hash, K key) {
        Node<K, V> node = first;
        while ((node.getHash() != hash || !node.getKey().equals(key)) && node.getNext() != null) {
            node = node.getNext();
        }
        return node;
    }

    private int hashCode(K key) {
        return key.hashCode();
    }
}

