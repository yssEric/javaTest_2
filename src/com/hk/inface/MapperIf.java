package com.hk.inface;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MapperIf<T> {

    T mapperObj(ResultSet res) throws SQLException;

    T mapperSave();
}
