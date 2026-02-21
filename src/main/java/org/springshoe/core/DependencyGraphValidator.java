package org.springshoe.core;

import org.springshoe.exception.CircularDependencyException;

import java.lang.reflect.Field;
import java.util.*;

/*
* used Kahn’s Algorithm here
* */
public class DependencyGraphValidator {
    private Map<Class<?>, List<Field>> dependenciesMap;

    public DependencyGraphValidator(Map<Class<?>, List<Field>> dependenciesMap) {
        this.dependenciesMap = dependenciesMap;
    }

    public List<Class<?>> validate() {

        Map<Class<?>, Integer> indegree = new HashMap<>();

        for (Class<?> cls : dependenciesMap.keySet()) {
            indegree.putIfAbsent(cls, 0);
        }

        for (Map.Entry<Class<?>, List<Field>> entry : dependenciesMap.entrySet()) {

            for (Field field : entry.getValue()) {
                Class<?> child = field.getType();
                indegree.putIfAbsent(child, 0);
                indegree.put(child, indegree.get(child) + 1);
            }
        }

        Queue<Class<?>> queue = new ArrayDeque<>();
        for (Map.Entry<Class<?>, Integer> e : indegree.entrySet()) {
            if (e.getValue() == 0) {
                queue.add(e.getKey());
            }
        }

        List<Class<?>> topoSortArray = new ArrayList<>();

        int processed = 0;
        while (!queue.isEmpty()) {
            Class<?> curr = queue.poll();
            topoSortArray.add(curr);
            processed++;

            for (Field nextField : dependenciesMap.getOrDefault(curr, List.of())) {
                Class<?> next = nextField.getType();
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) {
                    queue.add(next);
                }
            }
        }

        if (processed != indegree.size()) {
            throw new CircularDependencyException();
        }
        return topoSortArray;
    }
}
