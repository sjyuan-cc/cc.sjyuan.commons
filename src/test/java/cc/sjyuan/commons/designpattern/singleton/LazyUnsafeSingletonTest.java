package cc.sjyuan.commons.designpattern.singleton;

import org.junit.Test;

import static org.junit.Assert.*;

public class LazyUnsafeSingletonTest {

    @Test
    public void testGetInstance() throws Exception {
        LazyUnsafeSingleton lazyUnsafeSingleton = LazyUnsafeSingleton.getInstance();
        LazyUnsafeSingleton lazyUnsafeSingleton1 = LazyUnsafeSingleton.getInstance();
        assertEquals(lazyUnsafeSingleton,lazyUnsafeSingleton1);
    }
}