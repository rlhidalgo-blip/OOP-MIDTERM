public class ServiceSale extends SaleItem {

    private Service service;
    private Staff staff;

    public ServiceSale(Service service, Staff staff, double sellingPrice) {
        super(sellingPrice);
        this.service = service;
        this.staff = staff;
    }

    @Override
    public String getItemName() {
        return service.getName();
    }

    @Override
    public Staff getStaff() {
        return staff;
    }

    @Override
    public double calculateStaffCommission() {
        double commission =
                getSellingPrice() * service.getStaffCommissionRate();

        return Math.round(commission * 100.0) / 100.0;
    }

    @Override
    public double calculateBusinessShare() {
        double businessShare =
                getSellingPrice() * service.getBusinessShareRate();

        return Math.round(businessShare * 100.0) / 100.0;
    }
}