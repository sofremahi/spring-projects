package com.spring.batch.trial.domain;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductItemPreparedStatementSetter implements ItemPreparedStatementSetter<Product> {
    @Override
    public void setValues(Product item, PreparedStatement ps) throws SQLException {
        ps.setInt(1, item.getId());
        ps.setString(2, item.getName());
        ps.setString(3, item.getDescription());
        ps.setDouble(4, item.getPrice());
    }
}
