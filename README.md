# entity2transferobject
An easy to use Entity to Transfer Object mapper


usage : 

<h2>Available annotations </h2>
<ul>
<li> @TransferObject - <b>sourceClass</b> - eg : Account.class</li>
<li> @TransferObjectAttribute - <b>sourceField</b> - entity source field - eg : 'name'
<br/> If we had the following entity class :
<br/>
<code>
public class Account {
<br/>         private String name;
<br/>    }
<br/>  
</code>
<br /> And the following transfer object class:
<code>
<br/>
@TransferObject(sourceClass=Account.class)
<br/>
 public class AccountTO{
<br/>
 @TransferObjectAttribute(sourceField='name')
<br/>
  private String nameProperty;
</code>
</li>
</ul>

<h2>Converter manager initialization</h2>

<code>
        GenericConverterManager gcm = GenericConverterManager.getInstance();
       <br> gcm.setDebug(true);
       <br> gcm.setPackageName("com.norberth.test");
       
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
<code>
May 06, 2018 4:55:51 PM com.norberth.service.GenericConverterManager getConverter
  INFO:  Created new converter for class UserDetailsTO
<br/>May 06, 2018 4:55:51 PM com.norberth.service.GenericConverter tryToSetFieldValues
<br/>INFO: Setting target field 'name' value : Novanc
<br/>May 06, 2018 4:55:51 PM com.norberth.service.GenericConverter tryToSetFieldValues
<br/>INFO: Setting target field 'email' value : norberth.novanc@gmail.com
<br/>May 06, 2018 4:55:51 PM com.norberth.service.GenericConverter concatFieldsAndSetValue
<br/>INFO: Setting target field 'nameAndSurname' value : Novanc;Norberth;norberth.novanc@gmail.com;27
<br/>UserDetails{name='Novanc', age=27, email='norberth.novanc@gmail.com', password='password', surname='Norberth'}
<br/>UserDetailsTO{name='Novanc', email='norberth.novanc@gmail.com', nameAndSurname='Novanc;Norberth;norberth.novanc@gmail.com;27'}
</code>