package com.epam.wt.dao.implementation.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.wt.dao.INoteBookDAO;
import com.epam.wt.dao.implementation.ImplJDBCClassNotFoundException;
import com.epam.wt.dao.implementation.ImplSQLException;
import com.epam.wt.dao.implementation.ImplSourceNotFoundException;
import com.epam.wt.dao.implementation.ImplSourceRecordNotFoundException;
import com.epam.wt.dao.implementation.ImplIOException;
import com.epam.wt.entity.Note;
import com.epam.wt.jdbcutil.JDBCUtils;

public class NoteBookJDBCImpl implements INoteBookDAO {
	private static final NoteBookJDBCImpl instance = new NoteBookJDBCImpl();
	
	public static NoteBookJDBCImpl getInstance() {
		return instance;
	}
	
	private final String CONNECTION_STRING = "jdbc:mysql://127.0.0.1/test";
	private final String USER = "root";
	private final String PASSWORD = "123";
	private final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";

	@Override
	public void addNote(Note note) throws ImplIOException, ImplSQLException, 
			ImplJDBCClassNotFoundException {
		
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
			
			String addNoteQuery = JDBCUtils.makeInsertNoteQuery(note);
			
			statement = connection.createStatement();
			
			statement.executeUpdate(addNoteQuery);
			
			
		} catch (ClassNotFoundException e) {
			throw new ImplJDBCClassNotFoundException(e.getMessage());
		} catch (SQLException e) {
			throw new ImplSQLException(e.getMessage());
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new ImplSQLException(e.getMessage());
				}
			}
		}
	}

	@Override
	public Note getNoteAtIndex(int index) throws ImplSourceNotFoundException,
			ImplIOException, ImplSourceRecordNotFoundException, ImplSQLException, 
			ImplJDBCClassNotFoundException {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		Note note = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
			
			String getNoteQuery = "SELECT * FROM notebook";
			
			statement = connection.createStatement();
			
			rs = statement.executeQuery(getNoteQuery);
			
			for (int rsIndex = 0; rs.next(); rsIndex++) {
				if (rsIndex == index) {
					note = JDBCUtils.formNoteFromResultSetLine(rs);
					break;
				}
			}
			
		} catch (ClassNotFoundException e) {
			throw new ImplJDBCClassNotFoundException(e.getMessage());
		} catch (SQLException e) {
			throw new ImplSQLException(e.getMessage());
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new ImplSQLException(e.getMessage());
				}
			}
		}
		
		return note;
	}

	@Override
	public void removeNoteAtIndex(int index) throws ImplSourceNotFoundException,
			ImplIOException, ImplSourceRecordNotFoundException, ImplSQLException, 
			ImplJDBCClassNotFoundException {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
			
			String getNoteQuery = "SELECT * FROM notebook";
			
			statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE);
			
			rs = statement.executeQuery(getNoteQuery);
			
			int rsIndex = 0;
			while (rs.next()) {
				rsIndex++;
				if (rsIndex == index) {
					rs.deleteRow();
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			throw new ImplJDBCClassNotFoundException(e.getMessage());
		} catch (SQLException e) {
			throw new ImplSQLException(e.getMessage());
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new ImplSQLException(e.getMessage());
				}
			}
		}
	}

	@Override
	public void clearNotebook() throws ImplIOException, ImplSQLException, 
			ImplJDBCClassNotFoundException {
		
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
			
			String getNoteQuery = "TRUNCATE notebook";
			
			statement = connection.createStatement();
			
			statement.executeQuery(getNoteQuery);
			
		} catch (ClassNotFoundException e) {
			throw new ImplJDBCClassNotFoundException(e.getMessage());
		} catch (SQLException e) {
			throw new ImplSQLException(e.getMessage());
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new ImplSQLException(e.getMessage());
				}
			}
		}
	}

}
