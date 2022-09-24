package first;

import org.junit.Assert;
import org.junit.Test;

public class LRUCacheTest {
    private static final int CAPACITY = 3;

    private static final int KEY_A = 1;
    private static final int VALUE_A = 2;

    @Test
    public void testOneAdd() {
        var cache = new LRUCache<>(CAPACITY);
        cache.put(KEY_A, VALUE_A);
        Assert.assertEquals(1, cache.size());
        Assert.assertTrue(cache.containsKey(KEY_A));
        Assert.assertEquals(VALUE_A, cache.get(KEY_A));
    }

    private static final int KEY_B = 222;
    private static final int VALUE_B = 12;
    private static final int KEY_C = 0;
    private static final int VALUE_C = 21;
    private static final int KEY_D = 8;
    private static final int VALUE_D = 221;

    @Test
    public void testAddMany() {
        var cache = new LRUCache<>(CAPACITY);
        cache.put(KEY_A, VALUE_A);
        cache.put(KEY_B, VALUE_B);
        cache.put(KEY_C, VALUE_C);
        cache.put(KEY_D, VALUE_D);
        Assert.assertFalse(cache.containsKey(KEY_A));
        Assert.assertTrue(cache.containsKey(KEY_B));
        Assert.assertTrue(cache.containsKey(KEY_C));
        Assert.assertTrue(cache.containsKey(KEY_D));
    }

    @Test
    public void testAddManyAndGet() {
        var cache = new LRUCache<>(CAPACITY);
        cache.put(1, 2);
        cache.put(2, 3);
        cache.get(1);
        cache.put(4, 211);
        cache.put(8, 10);
        Assert.assertTrue(cache.containsKey(1));
        Assert.assertFalse(cache.containsKey(2));
        Assert.assertTrue(cache.containsKey(4));
        Assert.assertTrue(cache.containsKey(8));
    }

    @Test(expected = NullPointerException.class)
    public void testGetNull() {
        new LRUCache<>(CAPACITY).get(null);
    }

    @Test(expected = NullPointerException.class)
    public void testPutNull() {
        new LRUCache<>(CAPACITY).put(null, 2);
    }

    @Test
    public void testDelete() {
        var cache = new LRUCache<>(CAPACITY);
        cache.put(1, 2);
        cache.get(1);
        cache.remove(1);
        Assert.assertFalse(cache.containsKey(1));
    }
}
