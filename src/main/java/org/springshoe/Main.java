package org.springshoe;

import org.springshoe.annotation.Service;
import org.springshoe.core.DependencyGraphValidator;
import org.springshoe.core.HierarchyDependency;

import java.lang.reflect.Field;
import java.util.*;

/*
*
* attempt to create a graph to validate dependency graph before creating instances
* using Kahn’s Algorithm
*
**/
public class Main {
    static void main(){
        HierarchyDependency<?> hierarchyDependency = new HierarchyDependency<>(Service.class);
        Map<Class<?>,List<Field>> dependenciesMap = hierarchyDependency.populateHierarchy();
        DependencyGraphValidator validator = new DependencyGraphValidator(dependenciesMap);
        List<Class<?>> topoSortAray = validator.validate();
    }
}
