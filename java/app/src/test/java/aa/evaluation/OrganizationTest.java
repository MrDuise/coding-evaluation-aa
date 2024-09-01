package aa.evaluation;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class OrganizationTest {
    private MyOrganization organization;

    @Before
    public void setUp() {
        organization = new MyOrganization();
        organization.createOrganization(); // Initialize the organization structure
    }

    @Test
    public void testHireSuccess() {
        Name person = new Name("John", "Doe");
        Optional<Position> position = organization.hire(person, "VP Sales");

        assertTrue("Position should be found.", position.isPresent());
        assertTrue("Employee name should match.",
                position.get().getEmployee().isPresent() && position.get().getEmployee().get().getName().equals(person));
    }

    @Test
    public void testHireFailure() {
        Name person = new Name("Jane", "Doe");
        Optional<Position> position = organization.hire(person, "Nonexistent Title");

        assertFalse("Position should not be found.", position.isPresent());
    }

    @Test
    public void testOrganizationStructure() {
        Position ceo = organization.getRoot();

        assertEquals("CEO", ceo.getTitle());

        // Check President
        Optional<Position> president = ceo.getDirectReports().stream()
                .filter(p -> p.getTitle().equals("President"))
                .findFirst();
        assertTrue("President should exist.", president.isPresent());

        // Check direct reports of President
        assertPositionExists(president.get(), "VP Marketing");
        assertPositionExists(president.get(), "VP Finance");
        assertPositionExists(president.get(), "VP Sales");
        assertPositionExists(president.get(), "COO");

        // Check VP Sales' direct report
        Optional<Position> vpSales = findDirectReport(president.get(), "VP Sales");
        assertPositionExists(vpSales.get(), "Salesperson");

        // Check CIO
        Optional<Position> cio = ceo.getDirectReports().stream()
                .filter(p -> p.getTitle().equals("CIO"))
                .findFirst();
        assertTrue("CIO should exist.", cio.isPresent());

        // Check direct reports of CIO
        assertPositionExists(cio.get(), "VP Technology");
        assertPositionExists(cio.get(), "VP Infrastructure");

        // Check VP Technology's direct reports
        Optional<Position> vpTech = findDirectReport(cio.get(), "VP Technology");
        assertPositionExists(vpTech.get(), "Director Enterprise Architecture");
        assertPositionExists(vpTech.get(), "Director Customer Technology");
    }

    // Helper methods
    private void assertPositionExists(Position position, String title) {
        Optional<Position> report = findDirectReport(position, title);
        assertTrue(title + " should exist.", report.isPresent());
    }

    private Optional<Position> findDirectReport(Position position, String title) {
        return position.getDirectReports().stream()
                .filter(p -> p.getTitle().equals(title))
                .findFirst();
    }

}
