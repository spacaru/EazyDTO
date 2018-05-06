import com.norberth.service.GenericConverterManager;
import com.norberth.test.SimpleDTO;
import com.norberth.test.UserDetails;
import com.norberth.test.UserDetailsTO;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started app...");
        GenericConverterManager gcm = GenericConverterManager.getInstance();
        gcm.setDebug(false);
//        for testing purposes we test entities in test package
        gcm.setPackageName("com.norberth.test");
        System.out.println("Initialized GenericConverterManager");

//        Testing the manager
        UserDetails userDetails = new UserDetails("Novanc", new BigInteger("27"), "norberth.novanc@gmail.com", "password", "Norberth", new SimpleDTO("Novanc Norberth Gabriel"));
        UserDetailsTO userDetailsTO = (UserDetailsTO) gcm.getConverter(UserDetailsTO.class).getTo(userDetails);
        System.out.println(userDetails);
        System.out.println(userDetailsTO);
    }
}
