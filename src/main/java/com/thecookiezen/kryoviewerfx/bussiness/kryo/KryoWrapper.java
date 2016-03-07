package com.thecookiezen.kryoviewerfx.bussiness.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

public class KryoWrapper {
    private ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
        protected Kryo initialValue() {
            return pool.borrow();
        }
    };

    KryoFactory factory = () -> {
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
}
