package org.lutra.unmarshaller;

import org.lutra.unmarshaller.adapters.StringAdapter;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 06.08.2014 at 14:00
 * ResultSetUnmarshall of mysql-unmarshaller project
 *
 * @author Dmitry V. (savraz [at] gmail.com)
 */
public class ResultSetUnmarshall
{
	final private Map<Class, Class<? extends TypeAdapter>> registered_adapters = new HashMap<>();

	public ResultSetUnmarshall()
	{
		registered_adapters.put(String.class, StringAdapter.class);
	}

	private static int columnCount(ResultSet rs) throws SQLException
	{
		int cols = 0;
		if(rs != null)
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			cols = rsmd.getColumnCount();
		}

		return cols;
	}

	public <T> T asPOJO(ResultSet rs, Class<T> clazz) throws IllegalArgumentException, SQLException
	{
		T t;
		int total_columns = columnCount(rs);

		try
		{
			t = clazz.newInstance();
		}
		catch(InstantiationException | IllegalAccessException e)
		{
			throw new IllegalArgumentException(String.format("Cannot instantiate object of class %s", clazz), e);
		}
		Field[] fs = clazz.getDeclaredFields();
		for(Field f : fs)
		{
			String column_name = f.getName();
			if(f.isAnnotationPresent(Column.class))
			{
				Column ca = f.getAnnotation(Column.class);
				if(ca.name() != null)
					column_name = ca.name();
			}
			try
			{
				if(registered_adapters.get(f.getType()) != null)
					f.set(t, registered_adapters.get(f.getType()).newInstance().unmarshall(rs.getString(column_name)));
				else if(f.getType() == Integer.class || f.getType() == int.class)
					f.setInt(t, rs.getInt(column_name));
				else if(f.getType() == Double.class || f.getType() == double.class)
					f.setDouble(t, rs.getDouble(column_name));
				else if(f.getType() == Long.class || f.getType() == long.class)
					f.setLong(t, rs.getLong(column_name));
				else if(f.getType() == Boolean.class || f.getType() == boolean.class)
					f.setBoolean(t, rs.getBoolean(column_name));
				else if(f.getType() == Short.class || f.getType() == short.class)
					f.setShort(t, rs.getShort(column_name));
				else if(f.getType() == Short.class || f.getType() == short.class)
					f.setByte(t, rs.getByte(column_name));
				else if(f.getType() == Float.class || f.getType() == float.class)
					f.setFloat(t, rs.getFloat(column_name));
			}
			catch(IllegalAccessException e)
			{
				throw new IllegalArgumentException(String.format("Property %s of %s object is not accessible", f.getName(), clazz), e);
			}
			catch(InstantiationException e)
			{
				throw new IllegalArgumentException(String.format("Type adapter %s constructor is not accessible", registered_adapters.get(f.getType())), e);
			}
		}

		return t;
	}
}
