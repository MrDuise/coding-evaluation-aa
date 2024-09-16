package com.aa.act.interview.org;

import java.util.*;

public class Position {

    private String title;
    private Optional<Employee> employee;
    // Changed directReports to a Map where key is title and value is a list of positions with the same title
    private Map<String, List<Position>> directReports;

    public Position(String title) {
        this.title = title;
        employee = Optional.empty();
        directReports = new HashMap<>();
    }

    public Position(String title, Employee employee) {
        this(title);
        if (employee != null)
            setEmployee(Optional.of(employee));
    }

    public String getTitle() {
        return title;
    }

    public void setEmployee(Optional<Employee> employee) {
        this.employee = employee;
    }

    public Optional<Employee> getEmployee() {
        return employee;
    }

    public boolean isFilled() {
        return employee.isPresent();
    }


    public void addDirectReport(Position position) {
        if (position == null)
            throw new IllegalArgumentException("position cannot be null");

        directReports.computeIfAbsent(position.getTitle(), k -> new ArrayList<>()).add(position);
    }

    // Remove a direct report from the map based on title
    public boolean removeDirectReport(Position position) {
        if (position == null)
            return false;

        List<Position> positions = directReports.get(position.getTitle());
        if (positions != null) {
            return positions.remove(position);
        }
        return false;
    }

    // Retrieve all direct reports
    public Map<String, List<Position>> getDirectReports() {
        return directReports;
    }

    @Override
    public String toString() {
        return title + employee.map(e -> ": " + e.toString()).orElse("");
    }
}
