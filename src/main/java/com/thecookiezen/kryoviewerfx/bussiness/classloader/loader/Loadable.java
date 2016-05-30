package com.thecookiezen.kryoviewerfx.bussiness.classloader.loader;

public interface Loadable<T> {
    Class<?> loadFromSchema(T schema);
}
