package com.thecookiezen.kryoviewerfx.bussiness.type.generator;

public interface TypeGenerator<T> {
    Class<?> generate(T schema);
}
