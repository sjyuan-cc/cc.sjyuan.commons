package cc.sjyuan.commons.designpattern.singleton;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class EnumSingletonTest {

    @Test
    public void testSerialize() throws IOException, ClassNotFoundException {
        EnumSingleton preSingleton = EnumSingleton.INSTANCE;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(preSingleton);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        EnumSingleton enumSingleton = (EnumSingleton) ois.readObject();
        oos.close();
        ois.close();
        assertEquals(enumSingleton, preSingleton);
    }
}