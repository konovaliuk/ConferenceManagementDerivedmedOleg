package com.derivedmed.proj.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatmentCompilier {
    private static PreparedStatmentCompilier instance = new PreparedStatmentCompilier();
    private static Logger LOGGER = LogManager.getLogger(PreparedStatmentCompilier.class);

    public static PreparedStatmentCompilier getInstance() {
        return instance;
    }

    private PreparedStatmentCompilier() {
    }

    public static PreparedStatement setValues(PreparedStatement preparedStatement, Object... values) {
        for (int i = 0; i < values.length; i++) {
            try {
                preparedStatement.setObject(i + 1, values[i]);
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        return preparedStatement;
    }
}
