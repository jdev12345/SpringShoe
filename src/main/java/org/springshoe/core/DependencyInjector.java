package org.springshoe.core;

import org.springshoe.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyInjector {

    public Map<Class<?>, Object> getBeans(List<Class<?>> topoSortAray){
        Map<Class<?>, Object> beanMap = new HashMap<>();
        for(Class<?> clazz : topoSortAray){
            //  no constructors defined
            Constructor<?>[] constructor = clazz.getDeclaredConstructors();
            Object bean = null;
            for (Constructor<?> c : clazz.getDeclaredConstructors()) {
                int mod = c.getModifiers();

                if (!Modifier.isPublic(mod))continue;
                if (c.getParameterCount() == 0) {
                    try {
                        bean = populateNoArgsConstructor(clazz, c, beanMap);
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        throw new BeanCreationException(e);
                    }
                    break;
                }else{
                    try {
                        bean = populateAllArgsConstructor(clazz, c, beanMap);
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        throw new BeanCreationException(e);
                    }
                }
            }
            if(bean == null){
                throw new BeanCreationException(String.format("No constructor found for class %s", clazz));
            }
            beanMap.put(clazz, bean);
        }
        return beanMap;
    }

    private Object populateNoArgsConstructor(Class<?> clazz, Constructor<?> c, Map<Class<?>, Object> beanMap) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Object bean = c.newInstance();
        for(Field field : clazz.getDeclaredFields()){
            if(beanMap.containsKey(clazz)){
                field.setAccessible(true);
                field.set(bean, beanMap.get(field.getClass()));
            }else{
                throw new BeanCreationException("Unexpected Error Occurred");
            }
        }
        return bean;
    }

    private Object populateAllArgsConstructor(Class<?> clazz, Constructor<?> c, Map<Class<?>, Object> beanMap) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Object> paramClasses = new ArrayList<>();
        for(Class<?> cls : c.getParameterTypes()){
            if(beanMap.containsKey(cls)){
                paramClasses.add(beanMap.get(cls));
            }else{
                throw new BeanCreationException("Unexpected Error Occurred");
            }
        }
        return c.newInstance(paramClasses.toArray());
    }
}
