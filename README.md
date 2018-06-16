# Entity2TO

An easy to use Entity to Transfer Object mapper


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
* initialize a MapperFactory
* use mapperFactory methods to map objects
* JPA support to mapperFactory ( use setEntityManager to provide a connection to your database and then map DTO lists directly from DB using getToListFromSql and getToFromSql methods )

<H3>Intallation guide</H3></br>
1. Download github repository.</br>
2. Build the project using <code> mvn clean install </code></br>
3. Add the generated jar to your project

<h2>Mapper factory initialization</h2>
     <code> mapperFactory = MapperFactoryImpl.withDebugEnabled(true).withPackageName("com.norberth"); </code></br>

<h3> Generic converter usage </h3>
       <code>GenericConverter userDetailsConverter = gcm.getConverter(UserDetailsTO.class);</code><br/>
       <code>userDetailsConverter.getToList(Arrays.asList(userDetails, secUSer, secUser21, hjfkrsd));</code><br/>
       <code> <span>userDetailsConverter.getToList(someList) - returns List of transfer objects for the specified type from the sourceList </span></code><br/>
        <code><span>userDetailsConverter.getToListSortBy(someList,sortAttribute) - returns List of transfer objects created from sourceList sorted by the given attribute. throws an error if attribute is not found sourceList elements </span>
        </code><br/>
</code>

<h3>Examples in test package</h3>
