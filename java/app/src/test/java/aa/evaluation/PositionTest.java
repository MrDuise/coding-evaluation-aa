package aa.evaluation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    private MyOrganization org;
    private Position manager;
    private Position employee;

    @BeforeEach
    void setUp() {
        org = new MyOrganization();
        manager = new Position("Manager");
        employee = new Position("Employee");
    }


    @Test
    void testAddDirectReportSuccess() {
        boolean result = manager.addDirectReport(employee);
        assertTrue(result, "Direct report should be added successfully.");
        assertTrue(manager.getDirectReports().contains(employee), "Employee should be in the direct reports list.");
    }


    @Test
    void testAddDirectReportFailure() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addDirectReport(null);
        });
        assertEquals("position cannot be null", exception.getMessage(), "Exception message should match.");
    }
}