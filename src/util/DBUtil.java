package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database utility class for managing MySQL connections
 */
public class DBUtil {
    // Database configuration - MariaDB 12.0.2
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/patient_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root"; // MariaDB root password
    
    // Connection pool could be added here for production
    private static Connection connection = null;

    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MariaDB JDBC driver (also compatible with MySQL driver)
            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e1) {
                // Fallback to MySQL driver if MariaDB driver not found
                Class.forName("com.mysql.cj.jdbc.Driver");
            }
            
            // Create connection
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("MariaDB/MySQL JDBC Driver not found. Please add mariadb-java-client.jar or mysql-connector-java.jar to lib/ folder", e);
        }
    }

    /**
     * Close connection quietly without throwing exceptions
     * @param conn Connection to close
     */
    public static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Close statement quietly
     * @param stmt Statement to close
     */
    public static void closeQuietly(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    /**
     * Close PreparedStatement quietly
     * @param pstmt PreparedStatement to close
     */
    public static void closeQuietly(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing prepared statement: " + e.getMessage());
            }
        }
    }

    /**
     * Close ResultSet quietly
     * @param rs ResultSet to close
     */
    public static void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
    }

    /**
     * Test database connection
     * @return true if connection successful
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Initialize database schema (create tables if not exist)
     */
    public static void initializeDatabase() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            
            // Create users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(100), " +
                    "full_name VARCHAR(100), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "last_login TIMESTAMP NULL, " +
                    "is_active BOOLEAN DEFAULT TRUE" +
                    ")");
            
            // Create patients table
            stmt.execute("CREATE TABLE IF NOT EXISTS patients (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "age INT, " +
                    "sex VARCHAR(10), " +
                    "dob DATE, " +
                    "height DOUBLE, " +
                    "weight DOUBLE, " +
                    "bmi DOUBLE, " +
                    "job VARCHAR(100), " +
                    "phone VARCHAR(20), " +
                    "address TEXT, " +
                    "reference VARCHAR(100), " +
                    "blood_pressure VARCHAR(20), " +
                    "spo2 INT, " +
                    "disease TEXT, " +
                    "case_no VARCHAR(50), " +
                    "pending_money DOUBLE DEFAULT 0, " +
                    "follow_up_date DATE, " +
                    "previous_medicines TEXT, " +
                    "registration_date DATE, " +
                    "remark TEXT" +
                    ")");
            
            // Create follow_ups table
            stmt.execute("CREATE TABLE IF NOT EXISTS follow_ups (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "patient_id INT NOT NULL, " +
                    "visit_no INT, " +
                    "visit_date DATE, " +
                    "height DOUBLE, " +
                    "weight DOUBLE, " +
                    "bmi DOUBLE, " +
                    "blood_pressure VARCHAR(20), " +
                    "spo2 INT, " +
                    "next_follow_up_date DATE, " +
                    "nadi VARCHAR(50), " +
                    "samanya_lakshana TEXT, " +
                    "rx_treatment TEXT, " +
                    "days INT, " +
                    "total_payment DOUBLE, " +
                    "pending_payment DOUBLE, " +
                    "notes TEXT, " +
                    "kco TEXT, " +
                    "ho TEXT, " +
                    "sho TEXT, " +
                    "mh TEXT, " +
                    "oh TEXT, " +
                    "ah TEXT, " +
                    "mal VARCHAR(50), " +
                    "mutra VARCHAR(50), " +
                    "jivha VARCHAR(50), " +
                    "shudha VARCHAR(50), " +
                    "trushna VARCHAR(50), " +
                    "nidra VARCHAR(50), " +
                    "sweda VARCHAR(50), " +
                    "sparsha VARCHAR(50), " +
                    "druka VARCHAR(50), " +
                    "shabda VARCHAR(50), " +
                    "akruti VARCHAR(50), " +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE" +
                    ")");
            
            // Create panchakarma table
            stmt.execute("CREATE TABLE IF NOT EXISTS panchakarma (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "patient_id INT NOT NULL, " +
                    "panchakarma_name VARCHAR(100), " +
                    "advised VARCHAR(100), " +
                    "no_of_days INT, " +
                    "day INT, " +
                    "types_of_karma_and_medicines TEXT, " +
                    "price DOUBLE, " +
                    "duration_time VARCHAR(50), " +
                    "therapist_time VARCHAR(50), " +
                    "day_and_date DATE, " +
                    "note TEXT, " +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE" +
                    ")");
            
            // Create todo_items table
            stmt.execute("CREATE TABLE IF NOT EXISTS todo_items (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "user_id INT NOT NULL, " +
                    "task TEXT NOT NULL, " +
                    "is_completed BOOLEAN DEFAULT FALSE, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "completed_at TIMESTAMP NULL, " +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                    ")");
            
            System.out.println("Database schema initialized successfully");
            
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }
}

