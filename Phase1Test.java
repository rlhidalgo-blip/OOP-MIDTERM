public class Phase1Test {

    public static void main(String[] args) {

        // Test Staff objects
        Staff marco = new Staff("Marco", "Barber");
        Staff anna = new Staff("Anna", "Stylist");

        System.out.println("=== STAFF TEST ===");
        System.out.println("Name: " + marco.getName());
        System.out.println("Role: " + marco.getRole());
        System.out.println();

        System.out.println("Name: " + anna.getName());
        System.out.println("Role: " + anna.getRole());

        // Test fixed-price Service
        Service haircut = new Service(
                "Haircut",
                false,
                200.00,
                0.50,
                0.50
        );

        // Test variable-price Service
        Service hairColoring = new Service(
                "Hair Coloring",
                true,
                0.00,
                0.40,
                0.60
        );

        System.out.println("\n=== SERVICE TEST ===");

        System.out.println("Service: " + haircut.getName());
        System.out.println("Variable Price: " + haircut.isVariablePrice());
        System.out.printf(
                "Predefined Price: PHP %.2f%n",
                haircut.getPredefinedPrice()
        );
        System.out.println(
                "Staff Commission Rate: "
                        + haircut.getStaffCommissionRate()
        );
        System.out.println(
                "Business Share Rate: "
                        + haircut.getBusinessShareRate()
        );

        System.out.println();

        System.out.println("Service: " + hairColoring.getName());
        System.out.println(
                "Variable Price: " + hairColoring.isVariablePrice()
        );
        System.out.println(
                "Staff Commission Rate: "
                        + hairColoring.getStaffCommissionRate()
        );
        System.out.println(
                "Business Share Rate: "
                        + hairColoring.getBusinessShareRate()
        );

        // Test Product
        Product hairWax = new Product(
                "Hair Wax",
                250.00,
                50.00
        );

        System.out.println("\n=== PRODUCT TEST ===");
        System.out.println("Product: " + hairWax.getName());

        System.out.printf(
                "Selling Price: PHP %.2f%n",
                hairWax.getSellingPrice()
        );

        System.out.printf(
                "Fixed Staff Commission: PHP %.2f%n",
                hairWax.getFixedStaffCommission()
        );
    }
}