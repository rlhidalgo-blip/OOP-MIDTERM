import java.util.ArrayList;
import java.util.List;

public class Session {

    private List<Transaction> completedTransactions;
    private int nextTransactionNumber;

    public Session() {
        this.completedTransactions = new ArrayList<>();
        this.nextTransactionNumber = 1;
    }

    public Transaction startTransaction() {
        return new Transaction();
    }

    public void completeTransaction(
            Transaction transaction,
            PaymentMethod paymentMethod) {

        if (transaction == null) {
            return;
        }

        if (transaction.isCompleted()) {
            return;
        }

        transaction.complete(
                nextTransactionNumber,
                paymentMethod
        );

        if (transaction.isCompleted()) {
            completedTransactions.add(transaction);
            nextTransactionNumber++;
        }
    }

    public List<Transaction> getCompletedTransactions() {
        return new ArrayList<>(completedTransactions);
    }

    public Transaction findTransaction(int transactionNumber) {

        for (Transaction transaction : completedTransactions) {
            if (transaction.getTransactionNumber()
                    == transactionNumber) {

                return transaction;
            }
        }

        return null;
    }

    public int getCompletedTransactionCount() {
        return completedTransactions.size();
    }

    public double getTotalSales() {
        double total = 0.00;

        for (Transaction transaction : completedTransactions) {
            total += transaction.getTotalSales();
        }

        return roundToTwoDecimals(total);
    }

    public double getCashSales() {
        double total = 0.00;

        for (Transaction transaction : completedTransactions) {
            if (transaction.getPaymentMethod()
                    instanceof CashPayment) {

                total += transaction.getTotalSales();
            }
        }

        return roundToTwoDecimals(total);
    }

    public double getGCashSales() {
        double total = 0.00;

        for (Transaction transaction : completedTransactions) {
            if (transaction.getPaymentMethod()
                    instanceof GCashPayment) {

                total += transaction.getTotalSales();
            }
        }

        return roundToTwoDecimals(total);
    }

    public double getTotalStaffCommission() {
        double total = 0.00;

        for (Transaction transaction : completedTransactions) {
            total += transaction.getTotalStaffCommission();
        }

        return roundToTwoDecimals(total);
    }

    public double getTotalBusinessShare() {
        double total = 0.00;

        for (Transaction transaction : completedTransactions) {
            total += transaction.getTotalBusinessShare();
        }

        return roundToTwoDecimals(total);
    }

    public double getStaffCommission(Staff staff) {
        double total = 0.00;

        if (staff == null) {
            return total;
        }

        for (Transaction transaction : completedTransactions) {

            for (SaleItem item : transaction.getSaleItems()) {

                if (item.getStaff() == staff) {
                    total += item.calculateStaffCommission();
                }
            }
        }

        return roundToTwoDecimals(total);
    }

    private double roundToTwoDecimals(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}