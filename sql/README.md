# SQL Scripts for Patient Management System

This folder contains all SQL scripts needed to set up and manage the MySQL database for the Patient Management System.

## Files Overview

1. **01_create_database.sql** - Creates the database
2. **02_create_tables.sql** - Creates all required tables
3. **03_insert_sample_data.sql** - Inserts sample/test data
4. **04_useful_queries.sql** - Common queries for reporting and analysis

## Setup Instructions

### Step 1: Install MySQL

Download and install MySQL Server from: https://dev.mysql.com/downloads/mysql/

### Step 2: Create Database

Open MySQL command line or MySQL Workbench and run:

```bash
mysql -u root -p < 01_create_database.sql
```

Or manually:
```sql
source 01_create_database.sql
```

### Step 3: Create Tables

```bash
mysql -u root -p patient_management < 02_create_tables.sql
```

Or manually:
```sql
USE patient_management;
source 02_create_tables.sql
```

### Step 4: Insert Sample Data (Optional)

For testing purposes, you can insert sample data:

```bash
mysql -u root -p patient_management < 03_insert_sample_data.sql
```

### Step 5: Configure Application

Update the database credentials in `src/util/DBUtil.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/patient_management";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password_here";
```

## Database Schema

### Tables

1. **users** - User authentication and management
2. **patients** - Patient information
3. **follow_ups** - Patient follow-up visit records
4. **panchakarma** - Panchakarma treatment details
5. **todo_items** - Dashboard to-do list items

### Relationships

```
users (1) ----< (N) todo_items
patients (1) ----< (N) follow_ups
patients (1) ----< (N) panchakarma
```

## Common Operations

### Backup Database

```bash
mysqldump -u root -p patient_management > backup_$(date +%Y%m%d).sql
```

### Restore Database

```bash
mysql -u root -p patient_management < backup_20250128.sql
```

### Reset Database

```sql
DROP DATABASE patient_management;
source 01_create_database.sql
source 02_create_tables.sql
```

### View All Tables

```sql
USE patient_management;
SHOW TABLES;
```

### View Table Structure

```sql
DESCRIBE patients;
DESCRIBE follow_ups;
DESCRIBE panchakarma;
```

## Useful Queries

See `04_useful_queries.sql` for:
- Patient search queries
- Follow-up management queries
- Panchakarma treatment queries
- Statistical reports
- Revenue calculations
- Maintenance queries

## Security Notes

1. **Change Default Passwords**: The sample data includes default passwords. Change them immediately.
2. **Use Password Hashing**: Implement BCrypt or similar hashing for passwords in production.
3. **Limit Database Access**: Create separate MySQL users with limited privileges for the application.
4. **Regular Backups**: Set up automated daily backups.
5. **SSL Connection**: Use SSL for database connections in production.

## Creating Application User

For better security, create a dedicated MySQL user for the application:

```sql
-- Create user
CREATE USER 'patientapp'@'localhost' IDENTIFIED BY 'strong_password_here';

-- Grant privileges
GRANT SELECT, INSERT, UPDATE, DELETE ON patient_management.* TO 'patientapp'@'localhost';

-- Apply changes
FLUSH PRIVILEGES;
```

Then update `DBUtil.java` to use this user instead of root.

## Troubleshooting

### Connection Error

If you get "Access denied" error:
1. Check username and password in `DBUtil.java`
2. Verify MySQL service is running
3. Check MySQL port (default: 3306)

### Table Not Found

If you get "Table doesn't exist" error:
1. Verify database is selected: `USE patient_management;`
2. Run table creation script again
3. Check table names are correct (case-sensitive on Linux)

### Character Encoding Issues

If you see garbled text:
1. Ensure database uses UTF-8: `CHARACTER SET utf8mb4`
2. Set connection charset in JDBC URL:
   ```
   jdbc:mysql://localhost:3306/patient_management?useUnicode=true&characterEncoding=utf8
   ```

## Maintenance

### Regular Tasks

1. **Weekly**: Backup database
2. **Monthly**: Clean up old completed todos
3. **Quarterly**: Analyze and optimize tables
4. **Yearly**: Archive old patient records

### Optimize Tables

```sql
OPTIMIZE TABLE patients;
OPTIMIZE TABLE follow_ups;
OPTIMIZE TABLE panchakarma;
```

### Check Table Status

```sql
CHECK TABLE patients;
CHECK TABLE follow_ups;
```

## Support

For database-related issues, check:
1. MySQL error logs
2. Application logs
3. This README file
4. MySQL documentation: https://dev.mysql.com/doc/

