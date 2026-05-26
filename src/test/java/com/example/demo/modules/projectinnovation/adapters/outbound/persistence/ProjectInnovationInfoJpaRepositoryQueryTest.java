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
