package cc.sjyuan.commons.designpattern.singleton;

import org.junit.Test;

import static org.junit.Assert.*;

public class LazyHolderSingletonTest {

    @Test
    public void testGetInstance() throws Exception {
        LazyHolderSingleton lazyHolderSingleton = LazyHolderSingleton.getInstance();
        LazyHolderSingleton lazyHolderSingleton1 = LazyHolderSingleton.getInstance();
        assertEquals(lazyHolderSingleton, lazyHolderSingleton1);
    }
}