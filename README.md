jdbc-unmarshaller
=================

This library reflective maps **by name** fields of an object to columns of JDBC ```ResultSet``` row.

It iterates on an object fields, trying to find matching columns in a ```ResultSet```, and 
 throws an ```IllegalArgumentException``` if there's no such column found.
 
You can override this behavior, using a ```@Column(nullable=true)``` [annotation](#annotations). 

### [Example](https://github.com/Eternity-Yarr/jdbc-unmarshaller/blob/master/src/test/java/org/lutra/unmarshaller/UmTest.java)
Given some [POJO](https://github.com/Eternity-Yarr/jdbc-unmarshaller/blob/master/src/test/java/org/lutra/unmarshaller/TestPOJO.java) ```TestPOJO.java```

And MySQL table

```
+--------------+--------------+------+-----+---------+-------+
| Field        | Type         | Null | Key | Default | Extra |
+--------------+--------------+------+-----+---------+-------+
| int_field    | int(11)      | YES  |     | NULL    |       |
| string_field | varchar(255) | YES  |     | NULL    |       |
| double_field | float        | YES  |     | NULL    |       |
| my_int       | int(11)      | YES  |     | NULL    |       |
+--------------+--------------+------+-----+---------+-------+

```

with a few rows representing ```TestPOJO``` instances, we can select them and unmarshall to Java objects like that:

```java
ResultSetUnmarshall rsm = new ResultSetUnmarshall();
ResultSet rs = s.executeQuery("SELECT * FROM test_table");
while(rs.next())
{
	TestPOJO p = rsm.asPOJO(rs, TestPOJO.class);
	/* do some stuff with object, or simply print it */
	System.out.println(p); 
}
```

Or you can get them as List using ```asPOJOList(rs, TestPOJO.class)``` (this method will rewind cursor of ```ResultSet``` to first row prior to fetching). 

### Annotations

For some customization use  ```javax.persistence.Column``` annotation.

Currently this library supports this fields:

* ```name``` stands for some custom JDBC column name ``` @Column(name="my_int") ```
* ```updatable``` signals that this particular field shouldn't be updated by unmarshaller ``` @Column(updatable = false) ```
* ```nullable``` should be set to true if result set doesn't have column matching this property  

Also it's possible to unmarshall JDBC row to existing instance:

```java
TestPOJO p = new TestPOJO();
/* do some preinitialization */
rsm.asPOJO(p, rs, TestPOJO.class);
```

### Custom (non-primitive) types

To map column to custom types from String representation by creating type adapters, 
which extends [TypeAdapter](https://github.com/Eternity-Yarr/jdbc-unmarshaller/blob/master/src/main/java/org/lutra/unmarshaller/TypeAdapter.java) class,
like this dummy [StringAdapter](https://github.com/Eternity-Yarr/jdbc-unmarshaller/blob/master/src/main/java/org/lutra/unmarshaller/adapters/StringAdapter.java) implementation does.

And then register it on ```ResultSetUnmarshaller``` instance like this, before unmarshalling:
```java
rsm.registerAdapter(String.class, StringAdapter.class);
```
  