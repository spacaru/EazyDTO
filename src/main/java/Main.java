import com.norberth.service.GenericConverterManager;
import com.norberth.test.ComplicatedEntity;
import com.norberth.test.SimpleDTO;
import com.norberth.test.UserDetails;
import com.norberth.test.UserDetailsTO;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started app...");
        GenericConverterManager gcm = GenericConverterManager.getInstance();
        gcm.setDebug(true);
//        for testing purposes we test entities in test package
        gcm.setPackageName("com.norberth.test");
        System.out.println("Initialized GenericConverterManager");

//        Testing the manager
        UserDetails userDetails = new UserDetails("Novanc", new BigInteger("27"), "norberth.novanc@gmail.com", "password", "Norberth", new SimpleDTO("Novanc Norberth Gabriel", new ComplicatedEntity()));
        UserDetailsTO userDetailsTO = (UserDetailsTO) gcm.getConverter(UserDetailsTO.class).getTo(userDetails);
        UserDetails secUSer = new UserDetails("123", new BigInteger("34"), "432", "%43", "654", new SimpleDTO("123", new ComplicatedEntity()));
        List<UserDetailsTO> userDetailsTOList = (List) gcm.getConverter(UserDetailsTO.class).getToList(Arrays.asList(userDetails, secUSer,userDetails, secUSer,userDetails, secUSer,userDetails, secUSer,userDetails, secUSer,userDetails, secUSer));
        System.out.println(userDetailsTOList);
        System.out.println(userDetails);
        System.out.println(userDetailsTO);
    }
}
