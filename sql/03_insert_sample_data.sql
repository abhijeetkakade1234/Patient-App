-- Insert sample data for testing
-- Patient Management System

USE patient_management;

-- =====================================================
-- Insert sample users
-- =====================================================
-- Default password: "password123" (should be hashed in production)
INSERT INTO users (username, password, email, full_name, is_active) VALUES
('admin', 'password123', 'admin@clinic.com', 'Administrator', TRUE),
('doctor1', 'doctor123', 'doctor1@clinic.com', 'Dr. Rajesh Kumar', TRUE),
('receptionist', 'reception123', 'reception@clinic.com', 'Priya Sharma', TRUE);

-- =====================================================
-- Insert sample patients
-- =====================================================
INSERT INTO patients (name, age, sex, dob, height, weight, bmi, job, phone, address, 
                     blood_pressure, spo2, disease, case_no, registration_date, remark) VALUES
('Rahul Sharma', 35, 'Male', '1989-05-15', 175, 75, 24.5, 'Software Engineer', 
 '9876543210', 'Mumbai, Maharashtra', '120/80', 98, 'Hypertension', 'P001', '2025-01-15',
 'Regular patient, responds well to treatment'),
 
('Priya Patel', 28, 'Female', '1996-08-22', 162, 58, 22.1, 'Teacher', 
 '9876543211', 'Pune, Maharashtra', '118/76', 99, 'Migraine', 'P002', '2025-01-20',
 'Sensitive to certain medications'),
 
('Amit Singh', 42, 'Male', '1982-11-30', 168, 82, 29.0, 'Business Owner', 
 '9876543212', 'Delhi', '130/85', 97, 'Diabetes Type 2', 'P003', '2025-02-01',
 'Requires regular monitoring'),
 
('Sneha Reddy', 31, 'Female', '1993-03-10', 165, 62, 22.8, 'Marketing Manager', 
 '9876543213', 'Bangalore, Karnataka', '115/75', 98, 'Anxiety', 'P004', '2025-02-10',
 'Prefers natural remedies'),
 
('Vikram Joshi', 55, 'Male', '1969-07-18', 172, 78, 26.4, 'Retired', 
 '9876543214', 'Jaipur, Rajasthan', '140/90', 96, 'Arthritis', 'P005', '2025-02-15',
 'Long-term patient');

-- =====================================================
-- Insert sample follow-ups
-- =====================================================
INSERT INTO follow_ups (patient_id, visit_no, visit_date, height, weight, bmi, 
                       blood_pressure, spo2, next_follow_up_date, nadi, samanya_lakshana,
                       rx_treatment, days, total_payment, pending_payment, notes) VALUES
(1, 1, '2025-01-15', 175, 75, 24.5, '120/80', 98, '2025-02-15', 'Sama', 
 'Stable condition', 'Ayurvedic medicines - Ashwagandha, Brahmi', 30, 2500, 0, 
 'Initial consultation. Patient cooperative.'),
 
(1, 2, '2025-02-15', 175, 74, 24.2, '118/78', 99, '2025-03-15', 'Sama', 
 'Improvement noted', 'Continue same treatment', 30, 2000, 500, 
 'Blood pressure improving. Weight reduced slightly.'),
 
(2, 1, '2025-01-20', 162, 58, 22.1, '118/76', 99, '2025-02-20', 'Teevra', 
 'Frequent headaches', 'Shirodhara therapy recommended', 15, 3500, 1000, 
 'Migraine episodes 3-4 times per week.'),
 
(3, 1, '2025-02-01', 168, 82, 29.0, '130/85', 97, '2025-03-01', 'Manda', 
 'High blood sugar levels', 'Dietary modifications + Herbal supplements', 30, 3000, 0, 
 'HbA1c: 8.2%. Needs strict diet control.');

-- =====================================================
-- Insert sample Panchakarma treatments
-- =====================================================
INSERT INTO panchakarma (patient_id, panchakarma_name, advised, no_of_days, day,
                        types_of_karma_and_medicines, price, duration_time, 
                        therapist_time, day_and_date, note) VALUES
(2, 'Shirodhara', 'Dr. Rajesh Kumar', 7, 1, 
 'Medicated oil - Brahmi Taila', 1500, '45 minutes', '10:00 AM', '2025-02-21',
 'First session completed successfully'),
 
(2, 'Shirodhara', 'Dr. Rajesh Kumar', 7, 2, 
 'Medicated oil - Brahmi Taila', 1500, '45 minutes', '10:00 AM', '2025-02-22',
 'Patient feeling relaxed'),
 
(4, 'Abhyanga', 'Dr. Rajesh Kumar', 14, 1, 
 'Full body massage with herbal oils', 1200, '60 minutes', '11:00 AM', '2025-02-11',
 'Initial session for stress relief'),
 
(5, 'Janu Basti', 'Dr. Rajesh Kumar', 10, 1, 
 'Knee joint therapy with warm medicated oil', 1800, '40 minutes', '2:00 PM', '2025-02-16',
 'For arthritis pain management');

-- =====================================================
-- Insert sample to-do items
-- =====================================================
INSERT INTO todo_items (user_id, task, is_completed) VALUES
(1, 'Review patient reports for tomorrow', FALSE),
(1, 'Order medical supplies', FALSE),
(1, 'Call insurance company for claim P003', FALSE),
(2, 'Prepare treatment plan for new patients', TRUE),
(2, 'Update patient records', FALSE);

-- Display success message
SELECT 'Sample data inserted successfully!' AS Status;

-- Display counts
SELECT 
    (SELECT COUNT(*) FROM users) AS Total_Users,
    (SELECT COUNT(*) FROM patients) AS Total_Patients,
    (SELECT COUNT(*) FROM follow_ups) AS Total_FollowUps,
    (SELECT COUNT(*) FROM panchakarma) AS Total_Panchakarma,
    (SELECT COUNT(*) FROM todo_items) AS Total_TodoItems;

