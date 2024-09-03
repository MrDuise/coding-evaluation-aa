package aa.evaluation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;



public class OrganizationTest {
    private static MyOrganization organization;

    @BeforeAll
    public static void setUp() {
        organization = new MyOrganization();
        organization.createOrganization();
    }

    @Test
    public void testHireSuccess() {
        Name person = new Name("John", "Doe");
        Optional<Position> position = organization.hire(person, "VP Sales");

        assertTrue(position.isPresent(), "Position should be found.");
        assertTrue(position.get().getEmployee().isPresent() && position.get().getEmployee().get().getName().equals(person),
                "Employee name should match.");
    }

    @Test
    public void testHireFailure() {
        Name person = new Name("Jane", "Doe");
        Optional<Position> position = organization.hire(person, "Nonexistent Title");

        assertFalse(position.isPresent(), "Position should not be found.");
    }

    @Test
    public void testOrganizationStructure() {
        Position ceo = organization.getRoot();

        assertEquals("CEO", ceo.getTitle());


        Optional<Position> president = ceo.getDirectReports().stream()
                .filter(p -> p.getTitle().equals("President"))
                .findFirst();
        assertTrue(president.isPresent(), "President should exist.");


        assertPositionExists(president.get(), "VP Marketing");
        assertPositionExists(president.get(), "VP Finance");
        assertPositionExists(president.get(), "VP Sales");
        assertPositionExists(president.get(), "COO");


        Optional<Position> vpSales = findDirectReport(president.get(), "VP Sales");
        assertPositionExists(vpSales.get(), "Salesperson");


        Optional<Position> cio = ceo.getDirectReports().stream()
                .filter(p -> p.getTitle().equals("CIO"))
                .findFirst();
        assertTrue(cio.isPresent(), "CIO should exist.");


        assertPositionExists(cio.get(), "VP Technology");
        assertPositionExists(cio.get(), "VP Infrastructure");


        Optional<Position> vpTech = findDirectReport(cio.get(), "VP Technology");
        assertPositionExists(vpTech.get(), "Director Enterprise Architecture");
        assertPositionExists(vpTech.get(), "Director Customer Technology");
    }

    private void assertPositionExists(Position position, String title) {
        Optional<Position> report = findDirectReport(position, title);
        assertTrue(report.isPresent(), title + " should exist.");
    }

    private Optional<Position> findDirectReport(Position position, String title) {
        return position.getDirectReports().stream()
                .filter(p -> p.getTitle().equals(title))
                .findFirst();
    }

}
