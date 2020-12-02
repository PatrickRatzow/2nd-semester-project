package controller;

import entity.Requirement;
import entity.Specification;
import entity.specifications.Window;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class SpecificationController {
    private Specification specification;

    public SpecificationController(Specification specification) {
        this.specification = specification;
    }

    public List<Requirement> getRequirements() {
        return specification.getRequirements();
    }

    public static void main(String[] args) {
        SpecificationController ctr = new SpecificationController(new Window());

        Scanner scanner = new Scanner(System.in);
        for (Requirement req : ctr.getRequirements()) {
            String name = req.getName();
            System.out.println("Value for " + name);
            String value = scanner.nextLine();

            if (name.equals("Color")) {
                req.setValue(Color.BLUE);
            } else {
                req.setValue(Integer.parseInt(value));
            }
        }

        ctr.getRequirements().forEach(r -> System.out.println(r.getName() + " - " + r.getValue()));
    }
}
