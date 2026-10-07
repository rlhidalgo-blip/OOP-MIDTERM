public class Service {

    private String name;
    private boolean variablePrice;
    private double predefinedPrice;
    private double staffCommissionRate;
    private double businessShareRate;

    public Service(
            String name,
            boolean variablePrice,
            double predefinedPrice,
            double staffCommissionRate,
            double businessShareRate) {

        this.name = name;
        this.variablePrice = variablePrice;
        this.predefinedPrice = predefinedPrice;
        this.staffCommissionRate = staffCommissionRate;
        this.businessShareRate = businessShareRate;
    }

    public String getName() {
        return name;
    }

    public boolean isVariablePrice() {
        return variablePrice;
    }

    public double getPredefinedPrice() {
        return predefinedPrice;
    }

    public double getStaffCommissionRate() {
        return staffCommissionRate;
    }

    public double getBusinessShareRate() {
        return businessShareRate;
    }
}