-- Create database for Patient Management System
-- MySQL Database Setup Script

-- Drop database if exists (use with caution in production)
-- DROP DATABASE IF EXISTS patient_management;

-- Create database
CREATE DATABASE IF NOT EXISTS patient_management
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE patient_management;

-- Display success message
SELECT 'Database patient_management created successfully!' AS Status;

