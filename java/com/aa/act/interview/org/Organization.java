package com.aa.act.interview.org;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Organization {

    private Position root;
    private int employeeNumber;

    public Organization() {
        root = createOrganization();
        employeeNumber = 0;
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        Optional<Position> pos = findPosition(this.root, title);
        pos.ifPresent(position -> position.setEmployee(Optional.of(new Employee(++this.employeeNumber, person))));
        return pos;
    }


    private Optional<Position> findPosition(Position position, String title) {
        if (position == null) {
            return Optional.empty();
        }


        Map<String, List<Position>> directReportsByTitle = position.getDirectReports();

        // Check if the current position has the desired title
        if (position.getTitle().equals(title)) {
            return Optional.of(position);
        }

        // Check if there are any direct reports with the matching title
        if (directReportsByTitle.containsKey(title)) {
            List<Position> matchingPositions = directReportsByTitle.get(title);


            for (Position report : matchingPositions) {
                if(!report.isFilled()){
                    return Optional.of(report);  // return the first matching position that is not filled
                }

            }
        }


        for (List<Position> reportsList : directReportsByTitle.values()) {
            for (Position report : reportsList) {
                Optional<Position> result = findPosition(report, title);
                if (result.isPresent()) {
                    return result;
                }
            }
        }

        return Optional.empty();
    }


    @Override
    public String toString() {
        return printOrganization(root, "");
    }

    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");


        Map<String, List<Position>> directReportsByTitle = pos.getDirectReports();


        for (List<Position> reportsList : directReportsByTitle.values()) {
            for (Position report : reportsList) {
                sb.append(printOrganization(report, prefix + "  "));  // Recursive call with updated prefix
            }
        }

        return sb.toString();
    }

}
