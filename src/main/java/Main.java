import com.norberth.service.GenericConverter;
import com.norberth.service.GenericConverterManager;
import com.norberth.test.*;

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
//        UserDetails userDetails = new UserDetails(1, "Novanc", new BigInteger("27"), "norberth.novanc@gmail.com", "password", "Norberth", new SimpleDTO("Novanc Norberth Gabriel", Arrays.asList(new ComplicatedEntity(0), new ComplicatedEntity(-1), new ComplicatedEntity(-2))));
//        UserDetailsTO userDetailsTO = (UserDetailsTO) gcm.getConverter(UserDetailsTO.class).getTo(userDetails);
//        UserDetails secUSer = new UserDetails(2, "123", new BigInteger("34"), "andreea.barani@gmail.com", "%43", "654", new SimpleDTO("123", Arrays.asList(new ComplicatedEntity(1), new ComplicatedEntity(2), new ComplicatedEntity(3))));
//        UserDetails secUser21 = new UserDetails(3, "123", new BigInteger("34"), "bbb.barani@gmail.com", "%43", "654", new SimpleDTO("123", Arrays.asList(new ComplicatedEntity(6), new ComplicatedEntity(5), new ComplicatedEntity(4))));
//        UserDetails hjfkrsd = new UserDetails(4, "123", new BigInteger("34"), "bbb.barani@gmail.com", "%43", "654", new SimpleDTO("123", Arrays.asList(new ComplicatedEntity(7), new ComplicatedEntity(8), new ComplicatedEntity(9))));
//        List<UserDetailsTO> userDetailsTOList = (List) gcm.getConverter(UserDetailsTO.class).getToList(Arrays.asList(userDetails, secUSer, secUser21, hjfkrsd));
//        GenericConverter userDetailsConverter = gcm.getConverter(UserDetailsTO.class);
        Post post = new Post(1 , Arrays.asList(new Tag(1, "#hashtag")), "titlu", "desc", new User(1, "1", "2", "3", "4", Arrays.asList(new Post()), "123", true, true), true, 15.35f, 17.35f);
        PostTO postTO = (PostTO) gcm.getConverter(PostTO.class).getTo(post);

    }
}
