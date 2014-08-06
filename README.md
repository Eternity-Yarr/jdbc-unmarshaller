jdbc-unmarshaller
=================

This library reflective maps object fields to columns of JDBC ```ResultSet``` row.


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

with a few rows representing TestPOJO instances, we can select them and unmarshall to Java objects like that:

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

For some customization use  ```javax.persistence.Column``` annotation.

Currently this library supports ```name``` and ```updatable``` fields.

* ```name``` stands for some custom JDBC column name ``` @Column(name="my_int") ```
* ```updatable``` signals that this particular field shouldn't be updated by unmarshaller ``` @Column(updatable = false) ```

Also it's possible to unmarshall JDBC row to existing instance:

```java
TestPOJO p = new TestPOJO();
/* do some preinitialization */
rsm.asPOJO(p, rs, TestPOJO.class);
```
