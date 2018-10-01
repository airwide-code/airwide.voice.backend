package com.ebridgevas.vas.dto;

/**
 * @author david@tekeshe.com
 */
public class ProductIDKey {

    private final String serviceID;
    private final int categoryID;
    private final int productID;

    public ProductIDKey(String serviceID,
                        int categoryID,
                        int productID) {
        this.serviceID = serviceID;
        this.categoryID = categoryID;
        this.productID = productID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductIDKey that = (ProductIDKey) o;

        if (categoryID != that.categoryID) return false;
        if (productID != that.productID) return false;
        return !(serviceID != null ? !serviceID.equals(that.serviceID) : that.serviceID != null);

    }

    @Override
    public int hashCode() {
        int result = serviceID != null ? serviceID.hashCode() : 0;
        result = 31 * result + categoryID;
        result = 31 * result + productID;
        return result;
    }
}
