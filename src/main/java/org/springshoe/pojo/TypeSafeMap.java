package org.springshoe.pojo;

import java.util.Map;

public class TypeSafeMap {
    private Map<Class<?>, Object> store;

    public <T>void put(Class<T> cls, T instance){
        store.put(cls, cls.cast(instance));
    }

    public <T> T get(Class<T> cls){
        return cls.cast(store.get(cls));
    }

    public <T> boolean contains(Class<T> cls){
        return store.containsKey(cls);
    }
}
