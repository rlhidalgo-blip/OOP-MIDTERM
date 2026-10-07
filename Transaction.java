import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private int transactionNumber;
    private List<SaleItem> saleItems;
    private PaymentMethod paymentMethod;

    public Transaction() {
        this.transactionNumber = 0;
        this.saleItems = new ArrayList<>();
        this.paymentMethod = null;
    }

    public void addItem(SaleItem item) {
        if (!isCompleted() && item != null) {
            saleItems.add(item);
        }
    }

    public boolean hasItems() {
        return !saleItems.isEmpty();
    }

    public double getTotalSales() {
        double total = 0.00;

        for (SaleItem item : saleItems) {
            total += item.getSellingPrice();
        }

        return roundToTwoDecimals(total);
    }

    public double getTotalStaffCommission() {
        double total = 0.00;

        for (SaleItem item : saleItems) {
            total += item.calculateStaffCommission();
        }

        return roundToTwoDecimals(total);
    }

    public double getTotalBusinessShare() {
        double total = 0.00;

        for (SaleItem item : saleItems) {
            total += item.calculateBusinessShare();
        }

        return roundToTwoDecimals(total);
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public List<SaleItem> getSaleItems() {
        return new ArrayList<>(saleItems);
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isCompleted() {
        return transactionNumber > 0 && paymentMethod != null;
    }

    void complete(int transactionNumber, PaymentMethod paymentMethod) {
        if (isCompleted()) {
            return;
        }

        if (!hasItems()) {
            return;
        }

        if (transactionNumber <= 0) {
            return;
        }

        if (paymentMethod == null || !paymentMethod.isValid()) {
            return;
        }

        this.transactionNumber = transactionNumber;
        this.paymentMethod = paymentMethod;
    }

    private double roundToTwoDecimals(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}