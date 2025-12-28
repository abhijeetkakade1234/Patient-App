-- Create tables for Patient Management System
-- Run this after creating the database

USE patient_management;

-- =====================================================
-- Table: users
-- Description: Store user authentication information
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL COMMENT 'Should be hashed in production',
    email VARCHAR(100),
    full_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    is_active BOOLEAN DEFAULT TRUE,
    INDEX idx_username (username),
    INDEX idx_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: patients
-- Description: Store patient information
-- =====================================================
CREATE TABLE IF NOT EXISTS patients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT,
    sex VARCHAR(10),
    dob DATE,
    height DOUBLE COMMENT 'Height in centimeters',
    weight DOUBLE COMMENT 'Weight in kilograms',
    bmi DOUBLE COMMENT 'Body Mass Index',
    job VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    reference VARCHAR(100) COMMENT 'Reference/Referral source',
    blood_pressure VARCHAR(20),
    spo2 INT COMMENT 'Oxygen saturation percentage',
    disease TEXT,
    case_no VARCHAR(50),
    pending_money DOUBLE DEFAULT 0,
    follow_up_date DATE,
    previous_medicines TEXT,
    registration_date DATE,
    remark TEXT,
    INDEX idx_name (name),
    INDEX idx_phone (phone),
    INDEX idx_registration_date (registration_date),
    INDEX idx_follow_up_date (follow_up_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: follow_ups
-- Description: Store patient follow-up visit records
-- =====================================================
CREATE TABLE IF NOT EXISTS follow_ups (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    visit_no INT,
    visit_date DATE,
    height DOUBLE,
    weight DOUBLE,
    bmi DOUBLE,
    blood_pressure VARCHAR(20),
    spo2 INT,
    next_follow_up_date DATE,
    nadi VARCHAR(50) COMMENT 'Pulse examination',
    samanya_lakshana TEXT COMMENT 'General symptoms',
    rx_treatment TEXT COMMENT 'Treatment prescribed',
    days INT COMMENT 'Treatment duration in days',
    total_payment DOUBLE,
    pending_payment DOUBLE,
    notes TEXT,
    
    -- History fields
    kco TEXT COMMENT 'Known Case Of',
    ho TEXT COMMENT 'History Of',
    sho TEXT COMMENT 'Surgical History Of',
    mh TEXT COMMENT 'Medical History',
    oh TEXT COMMENT 'Occupational History',
    ah TEXT COMMENT 'Allergy History',
    
    -- Samanya Parikshan (General Examination) fields
    mal VARCHAR(50) COMMENT 'Bowel examination',
    mutra VARCHAR(50) COMMENT 'Urine examination',
    jivha VARCHAR(50) COMMENT 'Tongue examination',
    shudha VARCHAR(50) COMMENT 'Purity examination',
    trushna VARCHAR(50) COMMENT 'Thirst examination',
    nidra VARCHAR(50) COMMENT 'Sleep examination',
    sweda VARCHAR(50) COMMENT 'Sweat examination',
    sparsha VARCHAR(50) COMMENT 'Touch examination',
    druka VARCHAR(50) COMMENT 'Vision examination',
    shabda VARCHAR(50) COMMENT 'Sound/Voice examination',
    akruti VARCHAR(50) COMMENT 'Body structure examination',
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    INDEX idx_patient_id (patient_id),
    INDEX idx_visit_date (visit_date),
    INDEX idx_next_follow_up (next_follow_up_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: panchakarma
-- Description: Store Panchakarma treatment details
-- =====================================================
CREATE TABLE IF NOT EXISTS panchakarma (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    panchakarma_name VARCHAR(100),
    advised VARCHAR(100) COMMENT 'Advised by doctor',
    no_of_days INT COMMENT 'Total number of treatment days',
    day INT COMMENT 'Current day number',
    types_of_karma_and_medicines TEXT COMMENT 'Types of treatments and medicines used',
    price DOUBLE,
    duration_time VARCHAR(50) COMMENT 'Duration of each session',
    therapist_time VARCHAR(50) COMMENT 'Therapist schedule time',
    day_and_date DATE,
    note TEXT,
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    INDEX idx_patient_id (patient_id),
    INDEX idx_day_and_date (day_and_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: todo_items
-- Description: Store dashboard to-do list items
-- =====================================================
CREATE TABLE IF NOT EXISTS todo_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    task TEXT NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_completed (is_completed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Display success message
SELECT 'All tables created successfully!' AS Status;

