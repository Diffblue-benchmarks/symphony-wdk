package com.symphony.bdk.workflow.swadl;

import com.symphony.bdk.workflow.DoSomething;
import com.symphony.bdk.workflow.DoSomethingExecutor;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivityRegistryTest {

    @Test
    void getActivityTypesShouldReturnNonEmptySet() {
        // Arrange & Act
        Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

        // Assert
        assertNotNull(activityTypes);
        assertFalse(activityTypes.isEmpty());
    }

    @Test
    void getActivityTypesShouldReturnNewInstanceEachTime() {
        // Arrange & Act
        Set<Class<? extends BaseActivity>> activityTypes1 = ActivityRegistry.getActivityTypes();
        Set<Class<? extends BaseActivity>> activityTypes2 = ActivityRegistry.getActivityTypes();

        // Assert
        assertNotNull(activityTypes1);
        assertNotNull(activityTypes2);
        // Verify they are different instances (defensive copy)
        assertNotEquals(System.identityHashCode(activityTypes1), System.identityHashCode(activityTypes2));
    }

    @Test
    void getActivityTypesShouldReturnMutableCopy() {
        // Arrange
        Set<Class<? extends BaseActivity>> activityTypes1 = ActivityRegistry.getActivityTypes();
        int originalSize = activityTypes1.size();

        // Act
        activityTypes1.clear();
        Set<Class<? extends BaseActivity>> activityTypes2 = ActivityRegistry.getActivityTypes();

        // Assert
        assertTrue(activityTypes1.isEmpty());
        assertFalse(activityTypes2.isEmpty());
        // The second call should return the full set, unaffected by the clear
        assertTrue(activityTypes2.size() == originalSize);
    }

    @Test
    void findMatchingActivityWithValidExecutorShouldReturnActivityClass() throws Exception {
        // Arrange
        Method findMatchingActivity = ActivityRegistry.class.getDeclaredMethod("findMatchingActivity", Class.class);
        findMatchingActivity.setAccessible(true);

        // Act
        Class<? extends BaseActivity> result =
            (Class<? extends BaseActivity>) findMatchingActivity.invoke(null, DoSomethingExecutor.class);

        // Assert
        assertNotNull(result);
        assertEquals(DoSomething.class, result);
    }
}
