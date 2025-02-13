package dataTypes;

public class Pair<K, V> {
    private K key;
    private V value;
    public Pair(K k, V v) {
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K k) {
        key = k;
    }
    public void setValue(V v) {
        value = v;
    }
    @Override
    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }

}
