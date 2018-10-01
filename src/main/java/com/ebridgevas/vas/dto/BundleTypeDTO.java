package com.ebridgevas.vas.dto;

import java.math.BigDecimal;

public class BundleTypeDTO {

    private final String id;
    private final String name;
    private final BigDecimal price;
    private final Long bundleWindow;

    public BundleTypeDTO(String id,
                         String name,
                         BigDecimal price,
                         Long bundleWindow) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.bundleWindow = bundleWindow;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getBundleWindow() {
        return bundleWindow;
    }

    @Override
    public String toString() {
        return "BundleTypeDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", bundleWindow=" + bundleWindow +
                '}';
    }
}
