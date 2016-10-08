package cc.sjyuan.commons.designpattern.singleton;

import org.junit.Test;

import static org.junit.Assert.*;

public class LazyMethodSynSingletonTest {

    @Test
    public void testGetInstance() throws Exception {
        LazyMethodSynSingleton lazyMethodSynSingleton = LazyMethodSynSingleton.getInstance();
        LazyMethodSynSingleton lazyMethodSynSingleton1 =  LazyMethodSynSingleton.getInstance();
        assertEquals(lazyMethodSynSingleton,lazyMethodSynSingleton1);
    }
}