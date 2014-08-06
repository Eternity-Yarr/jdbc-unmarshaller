package org.lutra.unmarshaller;

import javax.persistence.Column;

/**
 * 06.08.2014 at 14:24
 * TestPOJO of jdbc-unmarshaller project
 *
 * @author Dmitry V. (savraz [at] gmail.com)
 */
public class TestPOJO
{
	int int_field;
	String string_field;
	double double_field;
	@Column(name="my_int")
	int int_field_custom_name;
	@Column(updatable = false)
	String pre_initialized = "With some value";

	@Override
	public String toString()
	{
		return "TestPOJO{" +
			"int_field=" + int_field +
			", string_field='" + string_field + '\'' +
			", double_field=" + double_field +
			", int_field_custom_name=" + int_field_custom_name +
			", pre_initialized='" + pre_initialized + '\'' +
			'}';
	}
}
