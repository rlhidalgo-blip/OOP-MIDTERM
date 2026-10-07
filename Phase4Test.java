public class Phase4Test {

    public static void main(String[] args) {

        Staff marco = new Staff("Marco", "Barber");
        Staff anna = new Staff("Anna", "Stylist");

        Service haircut = new Service(
                "Haircut",
                false,
                200.00,
                0.50,
                0.50
        );

        Service hairColoring = new Service(
                "Hair Coloring",
                true,
                0.00,
                0.40,
                0.60
        );

        Product hairWax = new Product(
                "Hair Wax",
                250.00,
                50.00
        );

        testMixedCashTransaction(
                marco,
                haircut,
                hairWax
        );

        testMixedGCashTransaction(
                anna,
                hairColoring,
                hairWax
        );

        testEmptyTransaction();

        testInvalidGCash(
                marco,
                haircut
        );
    }

    private static void testMixedCashTransaction(
            Staff marco,
            Service haircut,
            Product hairWax) {

        System.out.println(
                "=== TEST 1: MIXED CASH TRANSACTION ==="
        );

        Transaction transaction = new Transaction();

        SaleItem haircutSale = new ServiceSale(
                haircut,
                marco,
                haircut.getPredefinedPrice()
        );

        SaleItem waxSale = new ProductSale(
                hairWax,
                marco
        );

        transaction.addItem(haircutSale);
        transaction.addItem(waxSale);

        PaymentMethod payment = new CashPayment();

        transaction.complete(1, payment);

        printTransaction(transaction);
    }

    private static void testMixedGCashTransaction(
            Staff anna,
            Service hairColoring,
            Product hairWax) {

        System.out.println(
                "\n=== TEST 2: MIXED GCASH TRANSACTION ==="
        );

        Transaction transaction = new Transaction();

        SaleItem coloringSale = new ServiceSale(
                hairColoring,
                anna,
                1250.00
        );

        SaleItem waxSale = new ProductSale(
                hairWax,
                null
        );

        transaction.addItem(coloringSale);
        transaction.addItem(waxSale);

        PaymentMethod payment =
                new GCashPayment("123456789");

        transaction.complete(2, payment);

        printTransaction(transaction);
    }

    private static void testEmptyTransaction() {

        System.out.println(
                "\n=== TEST 3: EMPTY TRANSACTION ==="
        );

        Transaction transaction = new Transaction();

        transaction.complete(
                3,
                new CashPayment()
        );

        System.out.println(
                "Has Items: " + transaction.hasItems()
        );

        System.out.println(
                "Completed: " + transaction.isCompleted()
        );

        System.out.println(
                "Transaction Number: "
                        + transaction.getTransactionNumber()
        );
    }

    private static void testInvalidGCash(
            Staff marco,
            Service haircut) {

        System.out.println(
                "\n=== TEST 4: INVALID GCASH ==="
        );

        Transaction transaction = new Transaction();

        SaleItem haircutSale = new ServiceSale(
                haircut,
                marco,
                haircut.getPredefinedPrice()
        );

        transaction.addItem(haircutSale);

        PaymentMethod invalidPayment =
                new GCashPayment("ABC123");

        System.out.println(
                "Payment Valid: "
                        + invalidPayment.isValid()
        );

        transaction.complete(
                4,
                invalidPayment
        );

        System.out.println(
                "Completed: " + transaction.isCompleted()
        );

        System.out.println(
                "Transaction Number: "
                        + transaction.getTransactionNumber()
        );
    }

    private static void printTransaction(
            Transaction transaction) {

        System.out.println(
                "Transaction Number: #"
                        + transaction.getTransactionNumber()
        );

        System.out.println(
                "Completed: " + transaction.isCompleted()
        );

        System.out.println(
                "Number of Items: "
                        + transaction.getSaleItems().size()
        );

        System.out.println(
                "Payment: "
                        + transaction.getPaymentMethod().getName()
        );

        System.out.println(
                "Payment Details: "
                        + transaction.getPaymentMethod().getDetails()
        );

        System.out.printf(
                "Total Sales: PHP %.2f%n",
                transaction.getTotalSales()
        );

        System.out.printf(
                "Total Staff Commission: PHP %.2f%n",
                transaction.getTotalStaffCommission()
        );

        System.out.printf(
                "Total Business Share: PHP %.2f%n",
                transaction.getTotalBusinessShare()
        );

        System.out.println("Items:");

        for (SaleItem item : transaction.getSaleItems()) {

            System.out.printf(
                    "- %s | PHP %.2f | Commission PHP %.2f"
                            + " | Business Share PHP %.2f%n",
                    item.getItemName(),
                    item.getSellingPrice(),
                    item.calculateStaffCommission(),
                    item.calculateBusinessShare()
            );
        }
    }
}