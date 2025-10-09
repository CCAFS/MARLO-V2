-- SQL Validation for /api/innovations/stats endpoint
-- These queries should match the endpoint results exactly

USE aiccradb2;

SELECT '=== VALIDATION FOR STATS ENDPOINT ===' as title;

-- Test 1: innovationId=1566, phaseId=428 (ONLY ACTIVE INNOVATIONS)
-- Expected from endpoint: innovationCount: 1, countryCount: 2
SELECT 
    '1. Innovation 1566, Phase 428 (Active Only)' as test_case,
    COUNT(DISTINCT pic.project_innovation_id) as innovation_count,
    COUNT(DISTINCT pic.id_country) as country_count
FROM project_innovation_countries pic
JOIN project_innovations pi ON pic.project_innovation_id = pi.id
WHERE pi.is_active = 1 
  AND pic.project_innovation_id = 1566 
  AND pic.id_phase = 428;

-- Show details for debugging
SELECT 
    '1a. Details for Innovation 1566, Phase 428 (with Active Status)' as debug_info,
    pic.project_innovation_id, 
    pic.id_country, 
    pic.id_phase,
    pi.is_active,
    pi.active_since
FROM project_innovation_countries pic
JOIN project_innovations pi ON pic.project_innovation_id = pi.id
WHERE pic.project_innovation_id = 1566 AND pic.id_phase = 428;

-- Test 2: Only phaseId=428 (ONLY ACTIVE INNOVATIONS)
-- Expected from endpoint will now be different due to isActive filter
SELECT 
    '2. Only Phase 428 (Active Only)' as test_case,
    COUNT(DISTINCT pic.project_innovation_id) as innovation_count,
    COUNT(DISTINCT pic.id_country) as country_count
FROM project_innovation_countries pic
JOIN project_innovations pi ON pic.project_innovation_id = pi.id
WHERE pi.is_active = 1 
  AND pic.id_phase = 428;

-- Test 3: No filters (ONLY ACTIVE INNOVATIONS)
-- Expected from endpoint will now be different due to isActive filter
SELECT 
    '3. All data - Active Only (no other filters)' as test_case,
    COUNT(DISTINCT pic.project_innovation_id) as innovation_count,
    COUNT(DISTINCT pic.id_country) as country_count
FROM project_innovation_countries pic
JOIN project_innovations pi ON pic.project_innovation_id = pi.id
WHERE pi.is_active = 1;

-- Additional validation: Check if innovation 1566 has active status
SELECT 
    '4. Innovation 1566 status check' as validation,
    id,
    project_id,
    is_active,
    active_since
FROM project_innovations 
WHERE id = 1566;

-- Validate table structure
SELECT 
    '5. Table structure validation' as info,
    COUNT(*) as total_records
FROM project_innovation_countries;

-- Show sample data
SELECT 
    '6. Sample data from project_innovation_countries' as sample,
    id,
    project_innovation_id,
    id_country,
    id_phase
FROM project_innovation_countries 
ORDER BY id 
LIMIT 5;