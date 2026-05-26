package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectInnovationInfoJpaRepositoryQueryTest {

    @Test
    void statsCountQueriesShouldUseDedicatedNativePhaseQueries() throws NoSuchMethodException {
        assertStatsCountQueryIsPhaseSpecific(
                "countDistinctCountriesByPhase",
                "COUNT(DISTINCT pic.id_country)"
        );
        assertStatsCountQueryIsPhaseSpecific(
                "countDistinctInnovationsByPhase",
                "COUNT(DISTINCT pic.project_innovation_id)"
        );
    }

    @Test
    void filteredQueriesWithActorIdsShouldMatchAnySelectedActor() throws NoSuchMethodException {
        assertActorFilterUsesAnyMatch(
            "findActiveInnovationsInfoWithFilters",
            Long.class, Integer.class, Long.class, List.class, int.class, boolean.class,
            List.class, boolean.class
        );
        assertActorFilterUsesAnyMatch(
            "findActiveInnovationsInfoWithSearchFilters",
            Long.class, Integer.class, Long.class, List.class, int.class, boolean.class,
            List.class, boolean.class, String.class, boolean.class
        );
        assertActorFilterUsesAnyMatch(
            "findActiveInnovationsInfoBySdgFilters",
            Long.class, Long.class, Long.class, List.class, int.class, boolean.class,
            List.class, boolean.class
        );
        assertActorFilterUsesAnyMatch(
            "findActiveInnovationsInfoBySdgSearchFilters",
            Long.class, Long.class, Long.class, List.class, int.class, boolean.class,
            List.class, boolean.class, String.class, boolean.class
        );
    }

    private void assertStatsCountQueryIsPhaseSpecific(String methodName, String expectedSelect)
            throws NoSuchMethodException {
        Method method = ProjectInnovationCountryJpaRepository.class.getMethod(methodName, Long.class);
        Query query = method.getAnnotation(Query.class);

        assertNotNull(query, methodName + " should declare a @Query");
        assertTrue(query.nativeQuery(), methodName + " should use native SQL tuned for MySQL");
        assertTrue(
                query.value().contains(expectedSelect),
                methodName + " should count the expected distinct column"
        );
        assertTrue(
                query.value().contains("FROM project_innovation_countries pic"),
                methodName + " should use the real plural table name"
        );
        assertTrue(
                query.value().contains("JOIN project_innovations pi"),
                methodName + " should join active innovations directly"
        );
        assertFalse(
                query.value().contains(":phaseId IS NULL OR"),
                methodName + " should avoid optional OR predicates in the /stats path"
        );
    }

    private void assertActorFilterUsesAnyMatch(String methodName, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        Method method = ProjectInnovationInfoJpaRepository.class.getMethod(methodName, parameterTypes);
        Query query = method.getAnnotation(Query.class);

        assertNotNull(query, methodName + " should declare a @Query");
        assertTrue(
            query.value().contains(":hasActorFilter = false OR EXISTS"),
            methodName + " should use EXISTS so selected actors are treated as OR filters"
        );
        assertFalse(
            query.value().contains(") = :actorIdsCount"),
            methodName + " should not require every selected actor to be present"
        );
        assertFalse(
            hasParamNamed(method, "actorIdsCount"),
            methodName + " should not declare actorIdsCount because the query no longer uses it"
        );
    }

    private boolean hasParamNamed(Method method, String paramName) {
        for (Annotation[] annotations : method.getParameterAnnotations()) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof Param param && paramName.equals(param.value())) {
                    return true;
                }
            }
        }
        return false;
    }
}
