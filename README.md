# EazyDTO

An easy to use Entity -> Data Transfer Object annotation processor


usage : 

<h2>Available annotations </h2>
<ul>
<li> @MapObject - <b>fromClass</b> - eg : Account.class
  <br/>            - <b>value</b> - eg : 'account' </li>
<li> @MapAttribute - <b>value</b> - entity source field - eg : 'name'</li>
        <li> @MapList - <b>value</b>  - entity source field </li>
</ul>

<h2>Features </h2> </br>
-map any dto using @MapObject and @MapAttribute annotations </br>
-easily extensible ( implement CustomEvent<SourceEntity,TargetDTO> interface ) and add custom behavior inside postMap method </br>
-easy to install in any project : </br>
* initialize a MapperFactory </br>
* use mapperFactory methods to map objects </br>
* JPA support to mapperFactory ( use setEntityManager to provide a connection to your database and then map DTO lists directly from DB using getToListFromSql and getToFromSql methods ) </br>

<H3>Intallation guide</H3></br>
1. Download github repository.</br>
2. Build the project using <code> mvn clean install </code></br>
3. Add the generated jar to your project

<h2>Mapper factory initialization</h2>
     <code> mapperFactory = MapperFactoryImpl.withDebugEnabled(true).withPackageName("com.norberth"); </code></br>


<h4>Fragment from EntityDTO class</h4>
<code>
@MapObject(fromClass = Entity.class)
public class EntityDTO {

    @MapAttribute("intTest")
    private int intTest;
    @MapAttribute("doubleTest")

    private double doubleTest;
    @MapAttribute("stringTest")

    private String stringTest;
    @MapAttribute("floatTest")

    private float floatTest;
    @MapAttribute("boolTest")

    private boolean boolTest;
</code>
<h3> Generic converter usage </h3></br>
 <code>  EntityDTO createdDTO = (EntityDTO) genericMapperFactoryImpl.getMapper(EntityDTO.class).getTo(entity);</code></br>
<h1><b>More examples in test package</b></h1>


<h3> Contact </h3>
For any informations contact me at : norberth.novanc@gmail.com
