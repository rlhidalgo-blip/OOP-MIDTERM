public class Phase3Test {

    public static void main(String[] args) {

        PaymentMethod cash = new CashPayment();

        PaymentMethod validGCash =
                new GCashPayment("123456789");

        PaymentMethod emptyGCash =
                new GCashPayment("");

        PaymentMethod lettersGCash =
                new GCashPayment("ABC123");

        PaymentMethod symbolsGCash =
                new GCashPayment("123-456");

        System.out.println("=== VALID CASH ===");
        printPayment(cash);

        System.out.println("\n=== VALID GCASH ===");
        printPayment(validGCash);

        System.out.println("\n=== EMPTY GCASH REFERENCE ===");
        printPayment(emptyGCash);

        System.out.println("\n=== GCASH WITH LETTERS ===");
        printPayment(lettersGCash);

        System.out.println("\n=== GCASH WITH SYMBOLS ===");
        printPayment(symbolsGCash);

        System.out.println("\n=== POLYMORPHISM TEST ===");

        PaymentMethod payment;

        payment = new CashPayment();
        printPayment(payment);

        System.out.println();

        payment = new GCashPayment("0987654321");
        printPayment(payment);
    }

    private static void printPayment(PaymentMethod payment) {

        System.out.println(
                "Payment Name: " + payment.getName()
        );

        System.out.println(
                "Valid: " + payment.isValid()
        );

        System.out.println(
                "Details: " + payment.getDetails()
        );
    }
}