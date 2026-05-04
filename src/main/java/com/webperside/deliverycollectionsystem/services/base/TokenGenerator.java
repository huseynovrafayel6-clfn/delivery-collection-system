package com.webperside.deliverycollectionsystem.services.base;

public interface TokenGenerator <T> {

    String generate(T obj);

}
