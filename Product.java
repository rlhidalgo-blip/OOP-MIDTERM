public class Product {

    private String name;
    private double sellingPrice;
    private double fixedStaffCommission;

    public Product(
            String name,
            double sellingPrice,
            double fixedStaffCommission) {

        this.name = name;
        this.sellingPrice = sellingPrice;
        this.fixedStaffCommission = fixedStaffCommission;
    }

    public String getName() {
        return name;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getFixedStaffCommission() {
        return fixedStaffCommission;
    }
}