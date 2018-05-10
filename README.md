# Entity2TO

An easy to use Entity to Transfer Object mapper


usage : 

<h2>Available annotations </h2>
<ul>
<li> @TransferObject - <b>sourceClass</b> - eg : Account.class</li>
<li> @TransferObjectAttribute - <b>sourceField</b> - entity source field - eg : 'name'</li>
</ul>

<h2>Converter manager initialization</h2>
        <code>GenericConverterManager gcm = GenericConverterManager.getInstance();</code><br/>
        <code>gcm.setDebug(true);</code><br/>
        <code> gcm.setPackageName("com.norberth.test"); </code><br/>

<h3> Generic converter usage </h3>
       <code>GenericConverter userDetailsConverter = gcm.getConverter(UserDetailsTO.class);</code><br/>
       <code>userDetailsConverter.getToList(Arrays.asList(userDetails, secUSer, secUser21, hjfkrsd));</code><br/>
       <code> <span>userDetailsConverter.getToList(someList) - returns List of transfer objects for the specified type from the sourceList </span></code><br/>
        <code><span>userDetailsConverter.getToListSortBy(someList,sortAttribute) - returns List of transfer objects created from sourceList sorted by the given attribute. throws an error if attribute is not found sourceList elements </span>
        </code><br/>
</code>
<h2> Entity class </h2>
<code>
public class UserDetails {

    private String name;
    private BigInteger age;
    private String email;
    private String password;
    private String surname;
    private SimpleDTO simpleDTO;

    public UserDetails(String name, BigInteger age, String email, String password, String surname, SimpleDTO simpleDTO) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.surname = surname;
        this.simpleDTO = simpleDTO;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
</code>
<h2> Transfer object example class </h2>
<code>
@TransferObject(sourceClass = UserDetails.class)
<br/> public class UserDetailsTO {

    @TransferObjectAttribute(sourceField = "name")
    private String name;
    @TransferObjectAttribute(sourceField = "email")
    private String email;
    @TransferObjectAttribute(sourceField = "simpleDTO.fullName")
    private String simpleDtoFullName;
    @TransferObjectAttribute(sourceField = "name", concatFields = {"surname", "email", "age"}, separator = ";")
    private String nameAndSurname;

    @Override
    public String toString() {
        return "UserDetailsTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", simpleDtoFullName='" + simpleDtoFullName + '\'' +
                ", nameAndSurname='" + nameAndSurname + '\'' +
                '}';
    }
}
</code>

<h2> Converter usage </h2>
<code>
UserDetails userDetails = new UserDetails("Novanc", new BigInteger("27"), "norberth.novanc@gmail.com", "password", "Norberth");
<b>
<br/>UserDetailsTO userDetailsTO = (UserDetailsTO) gcm.getConverter(UserDetailsTO.class).getTo(userDetails);
</b>
                System.out.println(userDetails);
<br>                System.out.println(userDetailsTO);
                </code>
                
<h4>Output </h4>

<div>May 06, 2018 4:55:51 PM com.norberth.service.GenericConverterManager getConverter</div>
 <div> INFO:  Created new converter for class UserDetailsTO</div>
<div>May 06, 2018 4:55:51 PM com.norberth.service.GenericConverter tryToSetFieldValues</div>
<div>INFO: Setting target field 'name' value : Novanc</div>
<div>May 06, 2018 4:55:51 PM com.norberth.service.GenericConverter tryToSetFieldValues</div>
<div>INFO: Setting target field 'email' value : norberth.novanc@gmail.com</div>
<div>May 06, 2018 4:55:51 PM com.norberth.service.GenericConverter concatFieldsAndSetValue</div>
<div>INFO: Setting target field 'nameAndSurname' value : Novanc;Norberth;norberth.novanc@gmail.com;27</div>
<div>UserDetails{name='Novanc', age=27, email='norberth.novanc@gmail.com', password='password', surname='Norberth'}</div>
<div>UserDetailsTO{name='Novanc', email='norberth.novanc@gmail.com', nameAndSurname='Novanc;Norberth;norberth.novanc@gmail.com;27'}
