public class CashPayment implements PaymentMethod {

    public CashPayment() {
    }

    @Override
    public String getName() {
        return "Cash";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String getDetails() {
        return "No reference number required";
    }
}