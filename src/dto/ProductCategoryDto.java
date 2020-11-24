package dto;

import java.util.HashSet;
import java.util.Set;

public class ProductCategoryDto {
    private final int id;
    private final String name;
    private final String desc;
    private final Set<Integer> productIds = new HashSet<>();

    public ProductCategoryDto(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Set<Integer> getProductIds() {
        return productIds;
    }
}
