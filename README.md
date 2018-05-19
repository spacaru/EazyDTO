# Entity2TO

An easy to use Entity to Transfer Object mapper


usage : 

<h2>Available annotations </h2>
<ul>
<li> @MapObject - <b>sourceClass</b> - eg : Account.class</li>
<li> @MapAttribute - <b>sourceField</b> - entity source field - eg : 'name'</li>
        <li> @MapList - <b>sourceField</b>  - entity source field </li>
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

<h3>Examples in examples package</h3>
