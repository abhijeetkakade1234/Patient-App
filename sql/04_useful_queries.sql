-- Useful SQL queries for Patient Management System

USE patient_management;

-- =====================================================
-- PATIENT QUERIES
-- =====================================================

-- Get all patients with their latest follow-up date
SELECT 
    p.id,
    p.name,
    p.phone,
    p.registration_date,
    MAX(f.visit_date) AS last_visit,
    p.follow_up_date AS next_follow_up
FROM patients p
LEFT JOIN follow_ups f ON p.id = f.patient_id
GROUP BY p.id, p.name, p.phone, p.registration_date, p.follow_up_date
ORDER BY p.registration_date DESC;

-- Get patients registered today
SELECT * FROM patients 
WHERE DATE(registration_date) = CURDATE()
ORDER BY registration_date DESC;

-- Get patients registered this month
SELECT * FROM patients 
WHERE YEAR(registration_date) = YEAR(CURDATE()) 
  AND MONTH(registration_date) = MONTH(CURDATE())
ORDER BY registration_date DESC;

-- Get patients with pending payments
SELECT 
    p.id,
    p.name,
    p.phone,
    p.pending_money,
    SUM(f.pending_payment) AS follow_up_pending
FROM patients p
LEFT JOIN follow_ups f ON p.id = f.patient_id
WHERE p.pending_money > 0 OR f.pending_payment > 0
GROUP BY p.id, p.name, p.phone, p.pending_money
ORDER BY p.pending_money DESC;

-- Search patients by name or phone
SELECT * FROM patients 
WHERE name LIKE '%Sharma%' OR phone LIKE '%9876%'
ORDER BY name;

-- =====================================================
-- FOLLOW-UP QUERIES
-- =====================================================

-- Get all follow-ups for a specific patient
SELECT 
    f.*,
    p.name AS patient_name
FROM follow_ups f
JOIN patients p ON f.patient_id = p.id
WHERE f.patient_id = 1
ORDER BY f.visit_date DESC;

-- Get patients due for follow-up today
SELECT 
    p.id,
    p.name,
    p.phone,
    p.follow_up_date
FROM patients p
WHERE DATE(p.follow_up_date) = CURDATE()
ORDER BY p.name;

-- Get patients due for follow-up this week
SELECT 
    p.id,
    p.name,
    p.phone,
    p.follow_up_date
FROM patients p
WHERE p.follow_up_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)
ORDER BY p.follow_up_date;

-- Get follow-up count by patient
SELECT 
    p.id,
    p.name,
    COUNT(f.id) AS follow_up_count,
    MAX(f.visit_date) AS last_visit
FROM patients p
LEFT JOIN follow_ups f ON p.id = f.patient_id
GROUP BY p.id, p.name
ORDER BY follow_up_count DESC;

-- =====================================================
-- PANCHAKARMA QUERIES
-- =====================================================

-- Get all Panchakarma treatments for a patient
SELECT * FROM panchakarma 
WHERE patient_id = 2
ORDER BY day_and_date DESC;

-- Get ongoing Panchakarma treatments
SELECT 
    pk.*,
    p.name AS patient_name,
    p.phone
FROM panchakarma pk
JOIN patients p ON pk.patient_id = p.id
WHERE pk.day < pk.no_of_days
ORDER BY pk.day_and_date DESC;

-- Get Panchakarma revenue by month
SELECT 
    YEAR(day_and_date) AS year,
    MONTH(day_and_date) AS month,
    COUNT(*) AS treatment_count,
    SUM(price) AS total_revenue
FROM panchakarma
GROUP BY YEAR(day_and_date), MONTH(day_and_date)
ORDER BY year DESC, month DESC;

-- =====================================================
-- STATISTICS QUERIES
-- =====================================================

-- Dashboard statistics
SELECT 
    (SELECT COUNT(*) FROM patients) AS total_patients,
    (SELECT COUNT(*) FROM patients WHERE DATE(registration_date) = CURDATE()) AS today_patients,
    (SELECT COUNT(*) FROM patients WHERE YEAR(registration_date) = YEAR(CURDATE()) 
        AND MONTH(registration_date) = MONTH(CURDATE())) AS month_patients,
    (SELECT COUNT(*) FROM follow_ups) AS total_followups,
    (SELECT COUNT(*) FROM panchakarma) AS total_panchakarma;

-- Monthly patient registration trend
SELECT 
    YEAR(registration_date) AS year,
    MONTH(registration_date) AS month,
    COUNT(*) AS patient_count
FROM patients
GROUP BY YEAR(registration_date), MONTH(registration_date)
ORDER BY year DESC, month DESC
LIMIT 12;

-- Revenue statistics
SELECT 
    SUM(f.total_payment) AS total_follow_up_revenue,
    SUM(f.pending_payment) AS total_pending_follow_up,
    SUM(pk.price) AS total_panchakarma_revenue,
    SUM(p.pending_money) AS total_pending_patient
FROM patients p
LEFT JOIN follow_ups f ON p.id = f.patient_id
LEFT JOIN panchakarma pk ON p.id = pk.patient_id;

-- Patient age distribution
SELECT 
    CASE 
        WHEN age < 18 THEN 'Under 18'
        WHEN age BETWEEN 18 AND 30 THEN '18-30'
        WHEN age BETWEEN 31 AND 45 THEN '31-45'
        WHEN age BETWEEN 46 AND 60 THEN '46-60'
        ELSE 'Over 60'
    END AS age_group,
    COUNT(*) AS patient_count
FROM patients
WHERE age IS NOT NULL
GROUP BY age_group
ORDER BY age_group;

-- Disease distribution
SELECT 
    disease,
    COUNT(*) AS patient_count
FROM patients
WHERE disease IS NOT NULL AND disease != ''
GROUP BY disease
ORDER BY patient_count DESC
LIMIT 10;

-- =====================================================
-- MAINTENANCE QUERIES
-- =====================================================

-- Find patients without follow-ups
SELECT p.* 
FROM patients p
LEFT JOIN follow_ups f ON p.id = f.patient_id
WHERE f.id IS NULL;

-- Find duplicate phone numbers
SELECT phone, COUNT(*) AS count
FROM patients
WHERE phone IS NOT NULL
GROUP BY phone
HAVING COUNT(*) > 1;

-- Clean up old completed todos (older than 30 days)
DELETE FROM todo_items 
WHERE is_completed = TRUE 
  AND completed_at < DATE_SUB(NOW(), INTERVAL 30 DAY);

-- =====================================================
-- BACKUP QUERIES
-- =====================================================

-- Export patient data (use with mysqldump or SELECT INTO OUTFILE)
-- mysqldump -u root -p patient_management > backup_$(date +%Y%m%d).sql

-- Show database size
SELECT 
    table_schema AS 'Database',
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)'
FROM information_schema.tables
WHERE table_schema = 'patient_management'
GROUP BY table_schema;

