package com.thecookiezen.kryoviewerfx.bussiness.classloader.generator;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.Lists;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.KryoWrapper;
import com.thecookiezen.testing.User;
import org.junit.Before;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class ArrayTypeGeneratorTest {

    @Before
    public void setUp() throws FileNotFoundException {
        Kryo kryo = new KryoWrapper().getKryo();

        List<String> strings = Arrays.asList("qwe", "asd", "zxc");

        Output output = new Output(new FileOutputStream("arrayOfStrings.bin"));
        kryo.writeObject(output, strings);
        output.close();

        User user = new User(123, "John", "Miami");
        List<User> users = Lists.newArrayList(user);
        Output output2 = new Output(new FileOutputStream("arrayOfObjects.bin"));
        kryo.writeObject(output, users);
        output.close();
    }

}