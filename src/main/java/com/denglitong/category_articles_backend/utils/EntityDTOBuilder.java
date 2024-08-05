package com.denglitong.category_articles_backend.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/28
 */
public class EntityDTOBuilder {

    public static <E, D> E buildEntity(D dto, Class<E> entityClass) {
        try {
            E entity = entityClass.newInstance();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E, D> List<E> buildEntity(List<D> dtoList, Class<E> entityClass) {
        return dtoList.stream()
                .map(dto -> buildEntity(dto, entityClass))
                .collect(Collectors.toList());
    }

    public static <D, E> D buildDTO(E entity, Class<D> dtoClass) {
        try {
            D dto = dtoClass.newInstance();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <D, E> List<D> buildDTO(List<E> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> buildDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }
}
