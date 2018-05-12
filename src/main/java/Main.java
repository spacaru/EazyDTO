import com.norberth.service.GenericConverter;
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
        UserDetails userDetails = new UserDetails("Novanc", new BigInteger("27"), "norberth.novanc@gmail.com", "password", "Norberth", new SimpleDTO("Novanc Norberth Gabriel", Arrays.asList(new ComplicatedEntity(), new ComplicatedEntity(), new ComplicatedEntity())));
        UserDetailsTO userDetailsTO = (UserDetailsTO) gcm.getConverter(UserDetailsTO.class).getTo(userDetails);
        UserDetails secUSer = new UserDetails("123", new BigInteger("34"), "andreea.barani@gmail.com", "%43", "654", new SimpleDTO("123", Arrays.asList(new ComplicatedEntity(), new ComplicatedEntity(), new ComplicatedEntity())));
        UserDetails secUser21 = new UserDetails("123", new BigInteger("34"), "bbb.barani@gmail.com", "%43", "654", new SimpleDTO("123", Arrays.asList(new ComplicatedEntity(), new ComplicatedEntity(), new ComplicatedEntity())));
        UserDetails hjfkrsd = new UserDetails("123", new BigInteger("34"), "bbb.barani@gmail.com", "%43", "654", new SimpleDTO("123", Arrays.asList(new ComplicatedEntity(), new ComplicatedEntity(), new ComplicatedEntity())));
        List<UserDetailsTO> userDetailsTOList = (List) gcm.getConverter(UserDetailsTO.class).getToList(Arrays.asList(userDetails, secUSer, secUser21, hjfkrsd));
        GenericConverter userDetailsConverter = gcm.getConverter(UserDetailsTO.class);
        System.out.println(userDetails);
        System.out.println(userDetailsTO);
        System.out.println(userDetailsTOList);
    }
}
