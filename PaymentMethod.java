public interface PaymentMethod {

    String getName();

    boolean isValid();

    String getDetails();
}