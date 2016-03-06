package com.thecookiezen.testing;

public class BooleanSchema implements SimpleTypeSchema {
    @Override
    public String getName() {
        return "test1";
    }

    @Override
    public Class getType() {
        return boolean.class;
    }
}
