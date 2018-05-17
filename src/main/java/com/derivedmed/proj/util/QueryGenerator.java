package com.derivedmed.proj.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryGenerator {
    private StringBuilder querry;

    public QueryGenerator() {
        querry = new StringBuilder();
    }

    public QueryGenerator select(String param) {
        querry.append("select ").append(param.replaceAll(" ", "")).append(" ");
        return this;
    }

    public QueryGenerator update(String param) {
        querry.append("update ").append(param.replaceAll(" ", "")).append(" ");
        return this;
    }

    public QueryGenerator set(String[] params) {
        querry.append("set ");
        for (String param :
                params) {
            querry.append(param.replaceAll(" ", "")).append(" = ?, ");
        }
        querry = new StringBuilder(StringUtils.removeEnd(querry.toString(), ", ")).append(" ");
        return this;
    }

    public QueryGenerator from(String param) {
        querry.append("from ").append(param.replaceAll(" ", "")).append(" ");
        return this;
    }

    public QueryGenerator where(String param) {
        querry.append("where ").append(param.replaceAll(" ", "")).append(" = ? ");
        return this;
    }

    public QueryGenerator and(String param) {
        querry.append("and ").append(param.replaceAll(" ", "")).append(" = ? ");
        return this;
    }

    public QueryGenerator or(String param) {
        querry.append("or ").append(param.replaceAll(" ", "")).append(" ");
        return this;
    }

    public String generate() {
        return querry.toString();
    }

    public PreparedStatement setValues(PreparedStatement preparedStatement, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            try {
                preparedStatement.setObject(i + 1, values[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return preparedStatement;
    }
}
