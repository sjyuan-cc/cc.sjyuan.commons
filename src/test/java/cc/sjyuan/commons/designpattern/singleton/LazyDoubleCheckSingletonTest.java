package cc.sjyuan.commons.designpattern.singleton;

import org.junit.Test;

import static org.junit.Assert.*;

public class LazyDoubleCheckSingletonTest {

    @Test
    public void testGetInstance() throws Exception {
        LazyDoubleCheckSingleton lazyDoubleCheckSingleton = LazyDoubleCheckSingleton.getInstance();
        LazyDoubleCheckSingleton lazyDoubleCheckSingleton1 = LazyDoubleCheckSingleton.getInstance();
        assertEquals(lazyDoubleCheckSingleton,lazyDoubleCheckSingleton1);
    }
}