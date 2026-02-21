package org.springshoe;

import org.springshoe.annotation.Service;
import org.springshoe.core.HierarchyDependency;

import java.lang.reflect.Field;
import java.util.*;

/*
*
* Create a Class HierarchyDependency to handle multi level (Service, Dao, Controller) dependency
* injection
*
**/
public class Main {
    static void main(){
        HierarchyDependency<?> hierarchyDependency = new HierarchyDependency<>(Service.class);
        Map<Class<?>,List<Field>> dependenciesMap = hierarchyDependency.populateHierarchy();
    }
}
