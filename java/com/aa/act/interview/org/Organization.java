package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

    private Position root;
    private int employeeNumber = 0;

    public Organization() {
        root = createOrganization();
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


    private Optional<Position> findPosition(Position root, String title) {
        if (root == null) {
            return Optional.empty();
        }

        if (root.getTitle().equals(title)) {
            return Optional.of(root);
        }

        for (Position report : root.getDirectReports()) {
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
