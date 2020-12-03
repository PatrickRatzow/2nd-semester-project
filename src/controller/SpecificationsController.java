package controller;

import entity.Specification;
import entity.specifications.Roof;
import entity.specifications.Window;

import java.util.LinkedList;
import java.util.List;

public class SpecificationsController {
    public SpecificationsController() {}

    public List<Specification> getSpecifications() {
        final List<Specification> specifications = new LinkedList<>();
        specifications.add(new Window());
        specifications.add(new Roof());

        return specifications;
    }
}
