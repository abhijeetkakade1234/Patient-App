package dao;

import model.User;
import java.sql.SQLException;

/**
 * Data Access Object interface for User operations
 */
public interface UserDAO {
    
    int insert(User user) throws SQLException;
    
    boolean update(User user) throws SQLException;
    
    boolean delete(int userId) throws SQLException;
    
    User findById(int userId) throws SQLException;
    
    User findByUsername(String username) throws SQLException;
    
    boolean validateLogin(String username, String password) throws SQLException;
    
    boolean updatePassword(int userId, String newPassword) throws SQLException;
    
    boolean updateLastLogin(int userId) throws SQLException;
}

