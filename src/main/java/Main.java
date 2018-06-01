import com.norberth.factory.MapperFactoryImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started app...");
        MapperFactoryImpl gcm = MapperFactoryImpl.getInstance();
        gcm.setDebug(true);
//        for testing purposes we test entities in test package
        gcm.setPackageName("com.norberth.test");
        System.out.println("Initialized MapperFactoryImpl");

    }
}
