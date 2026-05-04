package com.webperside.deliverycollectionsystem.services.base;

public interface TokenReader<T> {
    T read(String token);
}
