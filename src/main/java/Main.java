import com.norberth.factory.GenericConverterFactory;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started app...");
        GenericConverterFactory gcm = GenericConverterFactory.getInstance();
        gcm.setDebug(true);
//        for testing purposes we test entities in test package
        gcm.setPackageName("com.norberth.test");
        System.out.println("Initialized GenericConverterFactory");

    }
}
