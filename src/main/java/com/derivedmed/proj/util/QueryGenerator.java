package com.derivedmed.proj.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryGenerator {
    private StringBuilder querry;

    public QueryGenerator() {
        querry = new StringBuilder();
    }

    public QueryGenerator select(String column) {
        querry.append("select ").append(column).append(" ");
        return this;
    }

    public QueryGenerator update(String table) {
        querry.append("update ").append(table).append(" ");
        return this;
    }

    public QueryGenerator insert(String table, String[] columns) {
        querry.append("insert into ").append(table).append(" ");
        int column = columns.length;
        querry.append("(");
        for (int i = 0; i < column - 1; i++) {
            querry.append(columns[i]).append(", ");
        }
        querry.append(columns[column - 1]).append(") ").append("values (");
        for (int i = 0; i < columns.length - 1; i++) {
            querry.append("?, ");
        }
        querry.append("? )");
        return this;
    }

    public QueryGenerator join(String table, String condition) {
        querry.append("join ").append(table).append(" on ").append(condition).append(" ");
        return this;
    }

    public QueryGenerator leftJoin(String table, String condition) {
        querry.append("left join ").append(table).append(" on ").append(condition).append(" ");
        return this;
    }

    public QueryGenerator rightJoin(String table, String condition) {
        querry.append("right join ").append(table).append(" on ").append(condition).append(" ");
        return this;
    }

    public QueryGenerator from(String column) {
        querry.append("from ").append(column).append(" ");
        return this;
    }

    public QueryGenerator set(String[] columns) {
        querry.append("set ");
        for (String param :
                columns) {
            querry.append(param).append(" = ?, ");
        }
        querry = new StringBuilder(StringUtils.removeEnd(querry.toString(), ", ")).append(" ");
        return this;
    }

    public QueryGenerator where(String column, String operator) {
        querry.append("where ").append(column).append(" ").append(operator).append(" ? ");
        return this;
    }

    public QueryGenerator and(String column, String operator) {
        querry.append("and ").append(column).append(" ").append(operator).append(" ? ");
        return this;
    }

    public QueryGenerator or(String column) {
        querry.append("or ").append(column).append(" ");
        return this;
    }

    public QueryGenerator delete(String column){
        querry.append("delete from ").append(column).append(" ");
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
