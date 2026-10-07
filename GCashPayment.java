public class GCashPayment implements PaymentMethod {

    private String referenceNumber;

    public GCashPayment(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @Override
    public String getName() {
        return "GCash";
    }

    @Override
    public boolean isValid() {
        if (referenceNumber == null || referenceNumber.isEmpty()) {
            return false;
        }

        for (int i = 0; i < referenceNumber.length(); i++) {
            if (!Character.isDigit(referenceNumber.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getDetails() {
        return "Reference Number: " + referenceNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }
}