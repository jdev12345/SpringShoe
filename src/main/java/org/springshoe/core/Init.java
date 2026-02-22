package org.springshoe.core;

import org.springshoe.annotation.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class Init {
    public static void init(){
        HierarchyDependency<?> hierarchyDependency = new HierarchyDependency<>(Service.class);
        Map<Class<?>, List<Field>> dependenciesMap = hierarchyDependency.populateHierarchy();
        DependencyGraphValidator validator = new DependencyGraphValidator(dependenciesMap);
        List<Class<?>> topoSortAray = validator.validate();
        DependencyInjector injector = new DependencyInjector();
        Map<Class<?>, Object> beanMap = injector.getBeans(topoSortAray);
    }

}
