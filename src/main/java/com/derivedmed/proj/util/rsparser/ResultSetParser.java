package com.derivedmed.proj.util.rsparser;

import com.derivedmed.proj.util.annotations.Column;
import com.derivedmed.proj.util.annotations.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultSetParser {
    private static Logger LOGGER = LogManager.getLogger(ResultSetParser.class);

    private static ResultSetParser instance = new ResultSetParser();

    public static ResultSetParser getInstance() {
        return instance;
    }

    private ResultSetParser() {
    }

    public <T> ArrayList<T> parse(ResultSet rs, T instance) {
        ArrayList<T> result = new ArrayList<>();
        Class clazz = instance.getClass();
        T resultUnit = instance;
        if (!resultUnit.getClass().isAnnotationPresent(Model.class)) {
            LOGGER.error("Can`t parse instance that isn`t model.");
            throw new IllegalArgumentException();
        }
        try {
            while (rs.next()) {
                result.add(setUpUnit(resultUnit,clazz,rs));
                resultUnit = (T) clazz.getConstructor().newInstance();
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception from parser", e);
        } catch (NoSuchMethodException e) {
            LOGGER.error("No such method!", e);
        } catch (InstantiationException e) {
            LOGGER.error("Instantiation exception!", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Can`t access to field", e);
        } catch (InvocationTargetException e) {
            LOGGER.error("Can`t invoke method", e);
        }
        return result;
    }
    private <T> T setUpUnit(T unit,Class clazz, ResultSet rs) throws IllegalAccessException, SQLException {
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Column.class)) {
                String name = f.getAnnotation(Column.class).name();
                f.setAccessible(true);
                Object object = rs.getObject(name);
                f.set(unit, object);
            }
        }
        return unit;
    }

}
