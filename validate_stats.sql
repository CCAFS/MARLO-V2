-- SQL Validation for /api/innovations/stats endpoint (Updated Version)
-- Endpoint now only accepts phaseId as required parameter
-- Expected endpoint call: GET /api/innovations/stats?phaseId=428

USE aiccradb2;

SELECT '=== VALIDATION FOR UPDATED STATS ENDPOINT ===' as title;

-- MAIN TEST: phaseId=428 (Current endpoint behavior)
-- Expected from endpoint: {"innovationCount": 63, "countryCount": 10, "phaseId": 428}
SELECT 
    'ENDPOINT QUERY - Phase 428 Statistics' as test_description,
    COUNT(DISTINCT pic.project_innovation_id) as innovation_count,
    COUNT(DISTINCT pic.id_country) as country_count,
    COUNT(*) as total_records_processed
FROM project_innovation_countries pic
JOIN project_innovations pi ON pic.project_innovation_id = pi.id
WHERE pi.is_active = 1 
  AND pic.id_phase = 428;

-- Show the actual countries found in phase 428
SELECT 
    'Countries in Phase 428' as info,
    GROUP_CONCAT(DISTINCT pic.id_country ORDER BY pic.id_country) as country_ids_list
FROM project_innovation_countries pic
JOIN project_innovations pi ON pic.project_innovation_id = pi.id
WHERE pi.is_active = 1 
  AND pic.id_phase = 428;

-- Final validation: Exact queries from endpoint
SELECT 
    'EXACT ENDPOINT QUERY 1 - Country Count' as endpoint_validation,
    COUNT(DISTINCT pic.id_country) as result
FROM project_innovation_countries pic 
JOIN project_innovations pi ON pic.project_innovation_id = pi.id 
WHERE pi.is_active = 1 
  AND pic.id_phase = 428;

SELECT 
    'EXACT ENDPOINT QUERY 2 - Innovation Count' as endpoint_validation,
    COUNT(DISTINCT pic.project_innovation_id) as result
FROM project_innovation_countries pic 
JOIN project_innovations pi ON pic.project_innovation_id = pi.id 
WHERE pi.is_active = 1 
  AND pic.id_phase = 428;
