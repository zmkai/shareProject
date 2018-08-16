package com.snsoft.dbSource;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbSource {
	public Connection getConnection() throws SQLException;
	public void closeConnect(Connection connection) throws SQLException;
}
