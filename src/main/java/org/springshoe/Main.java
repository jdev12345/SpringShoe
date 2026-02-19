package org.springshoe;

import org.springshoe.services.ServiceA;
import org.springshoe.services.ServiceB;
import org.springshoe.services.ServiceC;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
* this time i will use java reflection to do the same
*
*       A
*      / \
*     B   C
*
*
**/
@SuppressWarnings({ "rawtypes"})
public class Main {
    static void main() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class serviceBClass = Class.forName("org.springshoe.services.ServiceB");
        Class serviceCClass = Class.forName("org.springshoe.services.ServiceC");
        Class serviceAClass = Class.forName("org.springshoe.services.ServiceA");
        Constructor serviceBConstructor = serviceBClass.getDeclaredConstructor();
        Constructor serviceCConstructor = serviceCClass.getDeclaredConstructor();
        Constructor serviceAConstructor = serviceAClass.getConstructor(serviceBClass, serviceCClass);
        Object serviceB = serviceBConstructor.newInstance();
        Object serviceC = serviceCConstructor.newInstance();
        Object serviceA = serviceAConstructor.newInstance(serviceB, serviceC);
    }
}
