package cc.sjyuan.commons.designpattern.singleton;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class SerializableSingletonTest {

    @Test
    public void testGetInstance() throws Exception {
        SerializableSingleton serializableSingleton = SerializableSingleton.getInstance();
        SerializableSingleton serializableSingleton1 =SerializableSingleton.getInstance();
        assertEquals(serializableSingleton,serializableSingleton1);
    }

    @Test
    public void testSerialize() throws IOException, ClassNotFoundException {
        SerializableSingleton preSingleton = SerializableSingleton.getInstance();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(preSingleton);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        SerializableSingleton serializableSingleton = (SerializableSingleton) ois.readObject();
        oos.close();
        ois.close();
        assertEquals(serializableSingleton,preSingleton);
    }
}