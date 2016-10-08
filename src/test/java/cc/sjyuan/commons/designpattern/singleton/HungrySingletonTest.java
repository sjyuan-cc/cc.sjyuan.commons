package cc.sjyuan.commons.designpattern.singleton;

import org.junit.Test;

import static org.junit.Assert.*;

public class HungrySingletonTest {

    @Test
    public void testGetInstance() throws Exception {
        HungrySingleton instance = HungrySingleton.getInstance();
        HungrySingleton instance2 = HungrySingleton.getInstance();
        assertEquals(instance,instance2);
    }
}