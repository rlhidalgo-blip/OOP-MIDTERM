public class Phase2Test {

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

        // Fixed-price service sale
        ServiceSale haircutSale =
                new ServiceSale(
                        haircut,
                        marco,
                        haircut.getPredefinedPrice()
                );

        // Variable-price service sale
        ServiceSale coloringSale =
                new ServiceSale(
                        hairColoring,
                        anna,
                        1250.00
                );

        // Product sale with credited staff
        ProductSale creditedWaxSale =
                new ProductSale(hairWax, marco);

        // Product sale without credited staff
        ProductSale uncreditedWaxSale =
                new ProductSale(hairWax, null);

        System.out.println("=== FIXED-PRICE SERVICE SALE ===");
        printSaleItem(haircutSale);

        System.out.println("\n=== VARIABLE-PRICE SERVICE SALE ===");
        printSaleItem(coloringSale);

        System.out.println("\n=== CREDITED PRODUCT SALE ===");
        printSaleItem(creditedWaxSale);

        System.out.println("\n=== UNCREDITED PRODUCT SALE ===");
        printSaleItem(uncreditedWaxSale);
    }

    private static void printSaleItem(SaleItem item) {

        System.out.println("Item: " + item.getItemName());

        if (item.getStaff() == null) {
            System.out.println("Staff: None");
        } else {
            System.out.println(
                    "Staff: " + item.getStaff().getName()
            );
        }

        System.out.printf(
                "Selling Price: PHP %.2f%n",
                item.getSellingPrice()
        );

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