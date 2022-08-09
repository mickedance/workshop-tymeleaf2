package com.example.workshoptymeleaf2.converter;

import java.util.List;

public interface Converter<E, V> {
    V entityToView(E entity);
    List<V> entitiesToViews(List<E> entities);

    E viewToEntity(V view);
    List<E> viewsToEntities(List<V> views);
}
