public abstract class SaleItem {

    private double sellingPrice;

    protected SaleItem(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public abstract String getItemName();

    public abstract Staff getStaff();

    public abstract double calculateStaffCommission();

    public abstract double calculateBusinessShare();
}