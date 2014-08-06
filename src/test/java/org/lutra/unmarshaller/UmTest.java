package org.lutra.unmarshaller;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 06.08.2014 at 15:12
 * UmTest of jdbc-unmarshaller project
 *
 * @author Dmitry V. (savraz [at] gmail.com)
 */
public class UmTest
{
	@Test
	public void testUnmarshalling() throws  Exception
	{
/*
CREATE TABLE `test_table` (
  `int_field` int(11) DEFAULT NULL,
  `string_field` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `double_field` float DEFAULT NULL,
  `my_int` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO test_table (int_field, string_field, double_field, my_int) VALUES (123,"test string", 3.5, 23432), (323, "another row", 44.2, 454);
 */
		ResultSetUnmarshall rsm = new ResultSetUnmarshall();
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/testdb", "root", "abcdef");
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM test_table");
		while(rs.next())
		{
			TestPOJO p = rsm.asPOJO(rs, TestPOJO.class);
			System.out.println(p);
		}
		rs.close();
		s.close();
		con.close();
	}
}
