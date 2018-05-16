package com.derivedmed.proj.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CrudDao<T> extends Dao {

    int create(T t);

    T getByID(int id) throws NoSuchMethodException, InvocationTargetException;

    boolean update(T t);

    boolean delete(int id);

    List<T> getAll() throws NoSuchMethodException, InvocationTargetException;

    boolean clearAll();
}
