package com.aa.act.interview.org;

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

        if (position.getTitle().equals(title)) {
            return Optional.of(position);
        }

        for (Position report : position.getDirectReports()) {
            Optional<Position> result = findPosition(report, title);
            if (result.isPresent()) {
                return result;
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
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
