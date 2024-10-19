package com.spring.batch.trial.domain;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OsProductItemPreparedStatementSetter implements ItemPreparedStatementSetter<OsProduct> {
    @Override
    public void setValues(OsProduct item, PreparedStatement ps) throws SQLException {
        ps.setInt(1, item.getId());
        ps.setString(2, item.getName());
        ps.setString(3, item.getDescription());
        ps.setDouble(4, item.getPrice());
        ps.setDouble(5, item.getTax());
        ps.setDouble(6, item.getDiscount());
        ps.setDouble(7, item.getTaxPercent());
        ps.setDouble(8, item.getFinalPrice());
    }
}
