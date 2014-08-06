package org.lutra.unmarshaller;

import javax.persistence.Column;

/**
 * 06.08.2014 at 14:24
 * testPOJO of mysql-unmarshaller project
 *
 * @author Dmitry V. (savraz [at] gmail.com)
 */
public class testPOJO
{
	int int_field;
	String string_field;
	double double_field;
	@Column(name="my_int")
	int int_field_custom_name;
}
