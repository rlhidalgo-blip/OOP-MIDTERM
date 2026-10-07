public class ProductSale extends SaleItem {

    private Product product;
    private Staff creditedStaff;

    public ProductSale(Product product, Staff creditedStaff) {
        super(product.getSellingPrice());
        this.product = product;
        this.creditedStaff = creditedStaff;
    }

    @Override
    public String getItemName() {
        return product.getName();
    }

    @Override
    public Staff getStaff() {
        return creditedStaff;
    }

    @Override
    public double calculateStaffCommission() {
        if (creditedStaff == null) {
            return 0.00;
        }

        return product.getFixedStaffCommission();
    }

    @Override
    public double calculateBusinessShare() {
        double businessShare =
                getSellingPrice() - calculateStaffCommission();

        return Math.round(businessShare * 100.0) / 100.0;
    }
}