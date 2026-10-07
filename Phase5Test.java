public class Phase5Test {

    public static void main(String[] args) {

        // Predefined Staff
        Staff marco = new Staff("Marco", "Barber");
        Staff anna = new Staff("Anna", "Stylist");
        Staff john = new Staff("John", "Barber");

        // Predefined Services
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

        // Predefined Product
        Product hairWax = new Product(
                "Hair Wax",
                250.00,
                50.00
        );

        Session session = new Session();

        // ========================================
        // TRANSACTION 1 — CASH
        // Haircut by Marco
        // Credited Hair Wax to Marco
        // ========================================

        Transaction transaction1 =
                session.startTransaction();

        transaction1.addItem(
                new ServiceSale(
                        haircut,
                        marco,
                        haircut.getPredefinedPrice()
                )
        );

        transaction1.addItem(
                new ProductSale(
                        hairWax,
                        marco
                )
        );

        session.completeTransaction(
                transaction1,
                new CashPayment()
        );

        // ========================================
        // TRANSACTION 2 — GCASH
        // Hair Coloring by Anna at PHP 1,250
        // Uncredited Hair Wax
        // ========================================

        Transaction transaction2 =
                session.startTransaction();

        transaction2.addItem(
                new ServiceSale(
                        hairColoring,
                        anna,
                        1250.00
                )
        );

        transaction2.addItem(
                new ProductSale(
                        hairWax,
                        null
                )
        );

        session.completeTransaction(
                transaction2,
                new GCashPayment("123456789")
        );

        // ========================================
        // TRANSACTION 3 — CASH
        // Haircut by John
        // ========================================

        Transaction transaction3 =
                session.startTransaction();

        transaction3.addItem(
                new ServiceSale(
                        haircut,
                        john,
                        haircut.getPredefinedPrice()
                )
        );

        session.completeTransaction(
                transaction3,
                new CashPayment()
        );

        System.out.println(
                "=== COMPLETED TRANSACTIONS ==="
        );

        for (Transaction transaction
                : session.getCompletedTransactions()) {

            System.out.printf(
                    "Transaction #%d | PHP %.2f | %s%n",
                    transaction.getTransactionNumber(),
                    transaction.getTotalSales(),
                    transaction.getPaymentMethod().getName()
            );
        }

        System.out.println(
                "\n=== TRANSACTION COUNT ==="
        );

        System.out.println(
                "Completed Transactions: "
                        + session.getCompletedTransactionCount()
        );

        // ========================================
        // LOOKUP TEST
        // ========================================

        System.out.println(
                "\n=== LOOKUP TEST ==="
        );

        Transaction found =
                session.findTransaction(2);

        if (found != null) {
            System.out.println(
                    "Found Transaction #"
                            + found.getTransactionNumber()
            );
        } else {
            System.out.println(
                    "Transaction not found."
            );
        }

        // ========================================
        // SESSION TOTALS
        // ========================================

        printSessionSummary(
                session,
                marco,
                anna,
                john
        );

        // ========================================
        // RECONCILIATION TESTS
        // ========================================

        printReconciliationTests(session);

        // ========================================
        // FAILED EMPTY TRANSACTION
        // ========================================

        System.out.println(
                "\n=== FAILED EMPTY TRANSACTION ==="
        );

        int countBeforeFailure =
                session.getCompletedTransactionCount();

        double salesBeforeFailure =
                session.getTotalSales();

        Transaction emptyTransaction =
                session.startTransaction();

        session.completeTransaction(
                emptyTransaction,
                new CashPayment()
        );

        System.out.println(
                "Empty Transaction Completed: "
                        + emptyTransaction.isCompleted()
        );

        System.out.println(
                "Count Before: " + countBeforeFailure
        );

        System.out.println(
                "Count After: "
                        + session.getCompletedTransactionCount()
        );

        System.out.printf(
                "Sales Before: PHP %.2f%n",
                salesBeforeFailure
        );

        System.out.printf(
                "Sales After: PHP %.2f%n",
                session.getTotalSales()
        );

        // ========================================
        // FAILED INVALID GCASH TRANSACTION
        // ========================================

        System.out.println(
                "\n=== FAILED INVALID GCASH ==="
        );

        Transaction invalidGCashTransaction =
                session.startTransaction();

        invalidGCashTransaction.addItem(
                new ServiceSale(
                        haircut,
                        marco,
                        haircut.getPredefinedPrice()
                )
        );

        session.completeTransaction(
                invalidGCashTransaction,
                new GCashPayment("ABC123")
        );

        System.out.println(
                "Invalid GCash Transaction Completed: "
                        + invalidGCashTransaction.isCompleted()
        );

        System.out.println(
                "Completed Count: "
                        + session.getCompletedTransactionCount()
        );

        // ========================================
        // NEXT VALID TRANSACTION
        // Should become #4, not #5 or #6.
        // ========================================

        System.out.println(
                "\n=== NEXT VALID TRANSACTION ==="
        );

        Transaction transaction4 =
                session.startTransaction();

        transaction4.addItem(
                new ServiceSale(
                        haircut,
                        marco,
                        haircut.getPredefinedPrice()
                )
        );

        session.completeTransaction(
                transaction4,
                new CashPayment()
        );

        System.out.println(
                "New Transaction Number: #"
                        + transaction4.getTransactionNumber()
        );

        System.out.println(
                "Completed Count: "
                        + session.getCompletedTransactionCount()
        );

        System.out.printf(
                "New Total Sales: PHP %.2f%n",
                session.getTotalSales()
        );
    }

    private static void printSessionSummary(
            Session session,
            Staff marco,
            Staff anna,
            Staff john) {

        System.out.println(
                "\n=== SESSION SUMMARY ==="
        );

        System.out.printf(
                "Total Sales: PHP %.2f%n",
                session.getTotalSales()
        );

        System.out.printf(
                "Cash Sales: PHP %.2f%n",
                session.getCashSales()
        );

        System.out.printf(
                "GCash Sales: PHP %.2f%n",
                session.getGCashSales()
        );

        System.out.printf(
                "Total Staff Commission: PHP %.2f%n",
                session.getTotalStaffCommission()
        );

        System.out.printf(
                "Total Business Share: PHP %.2f%n",
                session.getTotalBusinessShare()
        );

        System.out.println(
                "\nSTAFF COMMISSIONS"
        );

        System.out.printf(
                "Marco: PHP %.2f%n",
                session.getStaffCommission(marco)
        );

        System.out.printf(
                "Anna: PHP %.2f%n",
                session.getStaffCommission(anna)
        );

        System.out.printf(
                "John: PHP %.2f%n",
                session.getStaffCommission(john)
        );
    }

    private static void printReconciliationTests(
            Session session) {

        System.out.println(
                "\n=== RECONCILIATION TESTS ==="
        );

        double paymentTotal =
                session.getCashSales()
                        + session.getGCashSales();

        double distributionTotal =
                session.getTotalStaffCommission()
                        + session.getTotalBusinessShare();

        System.out.printf(
                "Total Sales: PHP %.2f%n",
                session.getTotalSales()
        );

        System.out.printf(
                "Cash + GCash: PHP %.2f%n",
                paymentTotal
        );

        System.out.printf(
                "Commission + Business Share: PHP %.2f%n",
                distributionTotal
        );

        System.out.println(
                "Payment Reconciliation Correct: "
                        + (Math.abs(
                                paymentTotal
                                        - session.getTotalSales()
                        ) < 0.01)
        );

        System.out.println(
                "Financial Distribution Correct: "
                        + (Math.abs(
                                distributionTotal
                                        - session.getTotalSales()
                        ) < 0.01)
        );
    }
}