import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SalonApp {

    private Scanner scanner;

    private Session session;

    private List<Staff> staffList;
    private List<Service> serviceList;
    private List<Product> productList;

    public SalonApp() {
        this.scanner = new Scanner(System.in);
        this.session = new Session();

        this.staffList = new ArrayList<>();
        this.serviceList = new ArrayList<>();
        this.productList = new ArrayList<>();

        initializeData();
    }

    /*
     * Creates the predefined business data used by the
     * educational midterm application.
     */
    private void initializeData() {

        // Predefined Staff
        staffList.add(
                new Staff("Marco", "Barber")
        );

        staffList.add(
                new Staff("Anna", "Stylist")
        );

        staffList.add(
                new Staff("John", "Barber")
        );

        /*
         * Predefined Services
         *
         * PHP 200.00 for Haircut is sample midterm data.
         * It is not being treated as a verified current
         * real-world salon price.
         */

        serviceList.add(
                new Service(
                        "Haircut",
                        false,
                        200.00,
                        0.50,
                        0.50
                )
        );

        serviceList.add(
                new Service(
                        "Hair Coloring",
                        true,
                        0.00,
                        0.40,
                        0.60
                )
        );

        serviceList.add(
                new Service(
                        "Hair Treatment",
                        true,
                        0.00,
                        0.40,
                        0.60
                )
        );

        serviceList.add(
                new Service(
                        "Rebonding",
                        true,
                        0.00,
                        0.40,
                        0.60
                )
        );

        // Predefined Product
        productList.add(
                new Product(
                        "Hair Wax",
                        250.00,
                        50.00
                )
        );
    }

    /*
     * Program entry point.
     */
    public static void main(String[] args) {

        SalonApp app = new SalonApp();
        app.run();
    }

    /*
     * Main application loop.
     */
    public void run() {

        boolean running = true;

        System.out.println(
                "======================================"
        );
        System.out.println(
                " YAB'S SALON MINI OPERATIONS SYSTEM"
        );
        System.out.println(
                "======================================"
        );

        while (running) {

            displayMainMenu();

            int choice = readIntInRange(
                    "Choose an option: ",
                    1,
                    4
            );

            switch (choice) {

                case 1:
                    recordTransaction();
                    break;

                case 2:
                    viewTransactions();
                    break;

                case 3:
                    viewSessionSummary();
                    break;

                case 4:
                    running = false;
                    break;

                default:
                    // readIntInRange prevents this case.
                    break;
            }
        }

        System.out.println();
        System.out.println(
                "Exiting Yab's Salon Mini Operations System."
        );

        scanner.close();
    }

    private void displayMainMenu() {

        System.out.println();
        System.out.println("========== MAIN MENU ==========");
        System.out.println("1. Record Transaction");
        System.out.println("2. View Transactions");
        System.out.println("3. View Session Summary");
        System.out.println("4. Exit");
    }

    // =========================================================
    // INPUT HELPERS
    // =========================================================

    /*
     * Reads an integer that must fall within a specified range.
     */
    private int readIntInRange(
            String prompt,
            int minimum,
            int maximum) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {

                int value = Integer.parseInt(input);

                if (value >= minimum && value <= maximum) {
                    return value;
                }

                System.out.println(
                        "Invalid option. Please enter a number from "
                                + minimum
                                + " to "
                                + maximum
                                + "."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );
            }
        }
    }

    /*
     * Reads a positive monetary amount.
     */
    private double readPositivePrice(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {

                double value = Double.parseDouble(input);

                if (Double.isNaN(value)
                        || Double.isInfinite(value)) {

                    System.out.println(
                            "Invalid price. Please enter a valid amount."
                    );

                    continue;
                }

                double roundedValue =
                        roundToTwoDecimals(value);

                if (roundedValue > 0.00) {
                    return roundedValue;
                }

                System.out.println(
                        "Invalid price. Price must be greater than PHP 0.00."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a valid price."
                );
            }
        }
    }

    /*
     * Used only for consistent monetary input formatting.
     */
    private double roundToTwoDecimals(double amount) {

        return Math.round(amount * 100.0) / 100.0;
    }

    // =========================================================
    // RECORD TRANSACTION
    // =========================================================

    public void recordTransaction() {

        Transaction transaction =
                session.startTransaction();

        boolean addingItems = true;

        while (addingItems) {

            System.out.println();
            System.out.println(
                    "===== RECORD TRANSACTION ====="
            );

            System.out.println("1. Add Service");
            System.out.println("2. Add Product");
            System.out.println("3. Finish Adding Items");

            int choice = readIntInRange(
                    "Choose an option: ",
                    1,
                    3
            );

            switch (choice) {

                case 1:
                    addServiceToTransaction(transaction);
                    break;

                case 2:
                    addProductToTransaction(transaction);
                    break;

                case 3:

                    if (!transaction.hasItems()) {

                        System.out.println();
                        System.out.println(
                                "Cannot finish an empty transaction."
                        );

                        break;
                    }

                    boolean finished =
                            handlePreliminarySummary(
                                    transaction
                            );

                    if (finished) {
                        addingItems = false;
                    }

                    break;

                default:
                    break;
            }
        }
    }

    // =========================================================
    // ADD SERVICE
    // =========================================================

    private void addServiceToTransaction(
            Transaction transaction) {

        System.out.println();
        System.out.println("===== AVAILABLE SERVICES =====");

        for (int i = 0; i < serviceList.size(); i++) {

            Service service = serviceList.get(i);

            System.out.print(
                    (i + 1)
                            + ". "
                            + service.getName()
            );

            if (service.isVariablePrice()) {

                System.out.println(
                        " | Variable Price"
                );

            } else {

                System.out.printf(
                        " | PHP %.2f%n",
                        service.getPredefinedPrice()
                );
            }
        }

        int serviceChoice = readIntInRange(
                "Select a service: ",
                1,
                serviceList.size()
        );

        Service selectedService =
                serviceList.get(serviceChoice - 1);

        double sellingPrice;

        if (selectedService.isVariablePrice()) {

            sellingPrice = readPositivePrice(
                    "Enter final charged price: PHP "
            );

        } else {

            sellingPrice =
                    selectedService.getPredefinedPrice();

            System.out.printf(
                    "Predefined price: PHP %.2f%n",
                    sellingPrice
            );
        }

        Staff selectedStaff = selectStaff();

        ServiceSale serviceSale =
                new ServiceSale(
                        selectedService,
                        selectedStaff,
                        sellingPrice
                );

        transaction.addItem(serviceSale);

        System.out.println();
        System.out.println(
                selectedService.getName()
                        + " added to transaction."
        );
    }

    // =========================================================
    // ADD PRODUCT
    // =========================================================

    private void addProductToTransaction(
            Transaction transaction) {

        System.out.println();
        System.out.println("===== AVAILABLE PRODUCTS =====");

        for (int i = 0; i < productList.size(); i++) {

            Product product = productList.get(i);

            System.out.printf(
                    "%d. %s | PHP %.2f%n",
                    i + 1,
                    product.getName(),
                    product.getSellingPrice()
            );
        }

        int productChoice = readIntInRange(
                "Select a product: ",
                1,
                productList.size()
        );

        Product selectedProduct =
                productList.get(productChoice - 1);

        System.out.println();
        System.out.println(
                "Credit this product sale to a staff member?"
        );
        System.out.println("1. Yes");
        System.out.println("2. No");

        int creditChoice = readIntInRange(
                "Choose an option: ",
                1,
                2
        );

        Staff creditedStaff = null;

        if (creditChoice == 1) {
            creditedStaff = selectStaff();
        }

        ProductSale productSale =
                new ProductSale(
                        selectedProduct,
                        creditedStaff
                );

        transaction.addItem(productSale);

        System.out.println();
        System.out.println(
                selectedProduct.getName()
                        + " added to transaction."
        );
    }

    // =========================================================
    // STAFF SELECTION
    // =========================================================

    private Staff selectStaff() {

        System.out.println();
        System.out.println("===== STAFF =====");

        for (int i = 0; i < staffList.size(); i++) {

            Staff staff = staffList.get(i);

            System.out.println(
                    (i + 1)
                            + ". "
                            + staff.getName()
                            + " - "
                            + staff.getRole()
            );
        }

        int staffChoice = readIntInRange(
                "Select staff: ",
                1,
                staffList.size()
        );

        return staffList.get(staffChoice - 1);
    }

    // =========================================================
    // PRELIMINARY SUMMARY
    // =========================================================

    /*
     * Returns true when the transaction workflow should end.
     *
     * That happens after:
     * - successful payment/completion, or
     * - cancellation.
     *
     * Returns false when the cashier chooses to continue
     * adding items.
     */
    private boolean handlePreliminarySummary(
            Transaction transaction) {

        System.out.println();
        System.out.println(
                "===== PRELIMINARY TRANSACTION SUMMARY ====="
        );

        displayTransactionItems(transaction);

        System.out.println("------------------------------------------");

        System.out.printf(
                "Current Total: PHP %.2f%n",
                transaction.getTotalSales()
        );

        System.out.println();
        System.out.println("1. Proceed to Payment");
        System.out.println("2. Continue Adding Items");
        System.out.println("3. Cancel Transaction");

        int choice = readIntInRange(
                "Choose an option: ",
                1,
                3
        );

        switch (choice) {

            case 1:
                processPayment(transaction);
                return true;

            case 2:
                return false;

            case 3:
                System.out.println();
                System.out.println(
                        "Unfinished transaction cancelled."
                );

                /*
                 * The transaction is simply abandoned.
                 * Session never stores it and no transaction
                 * number is consumed.
                 */
                return true;

            default:
                return false;
        }
    }

    // =========================================================
    // PAYMENT
    // =========================================================

    private void processPayment(
            Transaction transaction) {

        System.out.println();
        System.out.println("===== PAYMENT =====");
        System.out.println("1. Cash");
        System.out.println("2. GCash");

        int paymentChoice = readIntInRange(
                "Select payment method: ",
                1,
                2
        );

        PaymentMethod paymentMethod;

        if (paymentChoice == 1) {

            paymentMethod = new CashPayment();

        } else {

            paymentMethod = readValidGCashPayment();
        }

        session.completeTransaction(
                transaction,
                paymentMethod
        );

        if (transaction.isCompleted()) {

            System.out.println();
            System.out.println(
                    "Transaction completed successfully."
            );

            displayTransactionDetails(transaction);

        } else {

            /*
             * With the workflow above this should not normally
             * occur because item/payment validity was checked,
             * but Transaction and Session remain the final
             * authority on successful completion.
             */
            System.out.println();
            System.out.println(
                    "Transaction could not be completed."
            );
        }
    }

    private PaymentMethod readValidGCashPayment() {

        while (true) {

            System.out.print(
                    "Enter GCash reference number: "
            );

            String reference =
                    scanner.nextLine().trim();

            PaymentMethod payment =
                    new GCashPayment(reference);

            if (payment.isValid()) {
                return payment;
            }

            System.out.println(
                    "Invalid GCash reference. "
                            + "Enter digits only and do not leave it empty."
            );
        }
    }

    // =========================================================
    // TRANSACTION DISPLAY HELPERS
    // =========================================================

    private void displayTransactionItems(
            Transaction transaction) {

        List<SaleItem> items =
                transaction.getSaleItems();

        for (int i = 0; i < items.size(); i++) {

            SaleItem item = items.get(i);

            System.out.println();
            System.out.println(
                    "Item " + (i + 1)
                            + ": "
                            + item.getItemName()
            );

            System.out.printf(
                    "Selling Price: PHP %.2f%n",
                    item.getSellingPrice()
            );

            Staff staff = item.getStaff();

            if (staff == null) {

                System.out.println(
                        "Staff: None"
                );

            } else {

                System.out.println(
                        "Staff: " + staff.getName()
                );
            }

            System.out.printf(
                    "Staff Commission: PHP %.2f%n",
                    item.calculateStaffCommission()
            );

            System.out.printf(
                    "Business Share: PHP %.2f%n",
                    item.calculateBusinessShare()
            );
        }
    }

    private void displayTransactionDetails(
            Transaction transaction) {

        System.out.println();
        System.out.println(
                "======================================"
        );

        System.out.println(
                "TRANSACTION #"
                        + transaction.getTransactionNumber()
        );

        System.out.println(
                "======================================"
        );

        displayTransactionItems(transaction);

        System.out.println();
        System.out.println("PAYMENT");
        System.out.println("--------------------------------------");

        PaymentMethod payment =
                transaction.getPaymentMethod();

        System.out.println(
                "Method: " + payment.getName()
        );

        /*
         * Only GCash has reference information that must
         * appear in the completed transaction details.
         */
        if (payment instanceof GCashPayment) {

            System.out.println(
                    payment.getDetails()
            );
        }

        System.out.println();
        System.out.println("TOTALS");
        System.out.println("--------------------------------------");

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

        System.out.println(
                "======================================"
        );
    }

    // =========================================================
    // VIEW TRANSACTIONS
    // =========================================================

    public void viewTransactions() {

        if (session.getCompletedTransactionCount() == 0) {

            System.out.println();
            System.out.println(
                    "No completed transactions in the current session."
            );

            return;
        }

        boolean viewing = true;

        while (viewing) {

            System.out.println();
            System.out.println(
                    "===== COMPLETED TRANSACTIONS ====="
            );

            for (Transaction transaction
                    : session.getCompletedTransactions()) {

                System.out.printf(
                        "#%d | PHP %.2f | %s%n",
                        transaction.getTransactionNumber(),
                        transaction.getTotalSales(),
                        transaction.getPaymentMethod().getName()
                );
            }

            System.out.println();

            int transactionNumber =
                    readNonNegativeInteger(
                            "Enter transaction number to view "
                                    + "(0 to return): "
                    );

            if (transactionNumber == 0) {

                viewing = false;

            } else {

                Transaction transaction =
                        session.findTransaction(
                                transactionNumber
                        );

                if (transaction == null) {

                    System.out.println(
                            "Transaction number does not exist "
                                    + "in the current session."
                    );

                } else {

                    displayTransactionDetails(
                            transaction
                    );

                    /*
                     * No extra menu is shown here.
                     * The loop naturally returns to the
                     * compact transaction list.
                     */
                }
            }
        }
    }

    private int readNonNegativeInteger(
            String prompt) {

        while (true) {

            System.out.print(prompt);

            String input =
                    scanner.nextLine().trim();

            try {

                int value =
                        Integer.parseInt(input);

                if (value >= 0) {
                    return value;
                }

                System.out.println(
                        "Invalid input. Please enter 0 "
                                + "or a positive transaction number."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );
            }
        }
    }

    // =========================================================
    // SESSION SUMMARY
    // =========================================================

    public void viewSessionSummary() {

        if (session.getCompletedTransactionCount() == 0) {

            System.out.println();
            System.out.println(
                    "No completed transactions in the current session."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "======================================"
        );
        System.out.println(
                "           SESSION SUMMARY"
        );
        System.out.println(
                "======================================"
        );

        System.out.println();
        System.out.println("COMPLETED TRANSACTIONS");
        System.out.println(
                session.getCompletedTransactionCount()
        );

        System.out.println();
        System.out.println("SALES");
        System.out.println("--------------------------------------");

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

        System.out.println();
        System.out.println(
                "FINANCIAL DISTRIBUTION"
        );
        System.out.println("--------------------------------------");

        System.out.printf(
                "Total Staff Commission: PHP %.2f%n",
                session.getTotalStaffCommission()
        );

        System.out.printf(
                "Total Business Share: PHP %.2f%n",
                session.getTotalBusinessShare()
        );

        System.out.println();
        System.out.println("STAFF COMMISSIONS");
        System.out.println("--------------------------------------");

        for (Staff staff : staffList) {

            System.out.printf(
                    "%s: PHP %.2f%n",
                    staff.getName(),
                    session.getStaffCommission(staff)
            );
        }

        System.out.println(
                "======================================"
        );

        waitForZero();
    }

    private void waitForZero() {

        while (true) {

            System.out.print(
                    "0. Return to Main Menu: "
            );

            String input =
                    scanner.nextLine().trim();

            if (input.equals("0")) {
                return;
            }

            System.out.println(
                    "Invalid option. Enter 0 to return."
            );
        }
    }
}
