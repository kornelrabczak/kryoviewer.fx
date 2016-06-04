package com.thecookiezen.kryoviewerfx.bussiness.classloader;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class KryoWrapper {
    private final ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
        protected Kryo initialValue() {
            return pool.borrow();
        }
    };

    private final KryoFactory factory = () -> {
        final Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        kryo.register(java.lang.invoke.SerializedLambda.class);
        try {
            kryo.register(Class.forName(Kryo.class.getName() + "$Closure"), new ClosureSerializer());
        } catch (ClassNotFoundException ignored) {
        }

        return kryo;
    };

    private final KryoPool pool = new KryoPool.Builder(factory).build();

    public Kryo getKryo() {
        return this.kryos.get();
    }

    public <T> T deserializeFromFile(File selectedFile, Class<T> clazz) throws FileNotFoundException {
        Input input = new Input(new FileInputStream(selectedFile));
        final T o = getKryo().readObject(input, clazz);
        input.close();
        System.out.println(o);
        return o;
    }
}
