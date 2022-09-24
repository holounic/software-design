package first;

import java.util.*;
import java.util.stream.Collectors;

public class LRUCache<T, U> implements Map<T, U> {
    private final int capacity;

    private final LinkedList list = new LinkedList();
    private final Map<T, Node> map;

    public LRUCache(int capacity) {
        assert capacity > 0;
        this.capacity = capacity;
        map = new HashMap<>(this.capacity);
    }

    @Override
    public int size() {
        assert map.size() <= capacity;
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public U get(Object key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Node node = map.get(key);
        list.moveFront(node);
        return node.value;
    }

    @Override
    public U put(T key, U value) {
        if (key == null) {
            throw new NullPointerException();
        }
        Node node;
        if (map.containsKey(key)) {
            node = map.get(key);
            list.moveFront(node);
            U oldValue = node.value;
            node.value = value;
            return oldValue;
        }
        node = new Node(key, value);
        if (size() == capacity) {
            Node last = list.lastNode;
            map.remove(last.key);
            list.removeLast();
        }
        list.addFirst(node);
        map.put(key, node);
        return value;
    }

    @Override
    public U remove(Object key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Node node = map.get(key);
        map.remove(key);
        list.removeNode(node);
        return node.value;
    }

    @Override
    public void putAll(Map<? extends T, ? extends U> m) {
        assert m.size() <= capacity;
        for (var e: m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        map.clear();
        list.firstNode = null;
        list.lastNode = null;
    }

    @Override
    public Set<T> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<U> values() {
        return map.values().stream().map(node -> node.value).collect(Collectors.toSet());
    }

    @Override
    public Set<Entry<T, U>> entrySet() {
        return map.entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey(), e.getValue().value))
                .collect(Collectors.toSet());
    }

    private class LinkedList {
        int size = 0;
        Node firstNode;
        Node lastNode;

        void removeNode(Node node) {
            if (firstNode == node) {
                firstNode = node.nextNode;
            }
            if (lastNode == node) {
                if (size == 2) {
                    lastNode = null;
                } else {
                    lastNode = node.prevNode;
                }
            }
            bindNodes(node.prevNode, node.nextNode);
            size--;
        }

        void moveFront(Node node) {
            if (node.prevNode == null)  {
                return;
            }
            bindNodes(node.prevNode, node.nextNode);
            size--;
            addFirst(node);
        }

        void removeFirst() {
            assert size > 0;
            firstNode = firstNode.nextNode;
            size--;
        }

        void removeLast() {
            assert size > 0;
            if (size == 1) {
                removeFirst();
                return;
            }
            lastNode = lastNode.prevNode;
            lastNode.nextNode = null;
            size--;
        }

        void addFirst(Node node) {
            bindNodes(node, firstNode);
            if (size == 1) {
                lastNode = firstNode;
            }
            firstNode = node;
            size++;
        }

        private void bindNodes(Node a, Node b) {
            if (a != null) {
                a.nextNode = b;
            }
            if (b != null) {
                b.prevNode = a;
            }
        }

        @Override
        public String toString() {
            return String.format("List[First = %s ... Last = %s] of size %d", firstNode, lastNode, size);
        }
    }

    private class Node {
        Node nextNode = null;
        Node prevNode = null;

        T key;
        U value;

        Node(T key, U value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("Node{key=%s, value=%s}", key, value);
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
