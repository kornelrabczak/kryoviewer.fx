package com.thecookiezen.kryoviewerfx.bussiness.schema.generator;

public interface TypeGenerator<T> {
    Class<?> generate(T schema);
}
