package com.ncedu.cheetahtest.dao.genericdao;

import lombok.Data;

@Data
public class Consts {

    private final String selectAllRowsFromTable;
    private final String tableName;

    public Consts(String[] rows, String tableName) {
        selectAllRowsFromTable = formatArrayIntoString(rows);
        this.tableName = tableName;
    }

    private String formatArrayIntoString(String[] rows) {
        StringBuilder select = new StringBuilder();

        for(int i = 0; i < rows.length; i++) {
            select.append(rows[i]);
            if (i < rows.length - 1) {
                select.append(", ");
            }
        }

        return select.toString();
    }

    private final String WHERE_STATUS_ACTIVE = " WHERE status = 'ACTIVE' ";
    private final String ORDER_PAGINATED = " ORDER BY id LIMIT ? OFFSET ? ";

    public String getActivePaginated() {
        return String.format(
                "SELECT %s " +
                "FROM %s " +
                WHERE_STATUS_ACTIVE +
                ORDER_PAGINATED,
                selectAllRowsFromTable, tableName);
    }

    public String getAllPaginated() {
        return String.format(
                "SELECT %s " +
                "FROM %s " +
                ORDER_PAGINATED,
                selectAllRowsFromTable, tableName);
    }

    public String getAmountActive() {
        return String.format(
                "SELECT count(id) " +
                "FROM %s " +
                WHERE_STATUS_ACTIVE,
                tableName);
    }

    public String getAmountAll() {
        return String.format(
                "SELECT count(id) " +
                "FROM %s ",
                tableName);
    }

    public String getById() {
        return String.format(
                "SELECT %s " +
                "FROM %s " +
                " WHERE id = ? LIMIT 1",
                selectAllRowsFromTable, tableName);
    }

    public String deactivate() {
        return String.format(
                "UPDATE %s " +
                "SET status = 'INACTIVE'::status " +
                " WHERE id = ?",
                tableName);
    }

    public String getActiveSearched() {
        return String.format(
                "SELECT %s " +
                "FROM %s " +
                WHERE_STATUS_ACTIVE +
                " AND title LIKE ? " +
                ORDER_PAGINATED,
                selectAllRowsFromTable, tableName);
    }

    public String getAllSearched() {
        return String.format(
                "SELECT %s " +
                "FROM %s " +
                " WHERE title LIKE ? " +
                ORDER_PAGINATED,
                selectAllRowsFromTable, tableName);
    }

    public String getAmountActiveSearched() {
        return String.format(
                "SELECT count(id) " +
                "FROM %s " +
                WHERE_STATUS_ACTIVE +
                " AND title LIKE ? LIMIT 1",
                tableName);
    }

    public String getAmountAllSearched() {
        return String.format(
                "SELECT count(id) " +
                "FROM %s " +
                "WHERE title LIKE ? LIMIT 1",
                tableName);
    }
}
