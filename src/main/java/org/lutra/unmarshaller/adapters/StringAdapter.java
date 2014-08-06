package org.lutra.unmarshaller.adapters;

import org.lutra.unmarshaller.TypeAdapter;

/**
 * 06.08.2014 at 14:58
 * StringAdapter of mysql-unmarshaller project
 *
 * @author Dmitry V. (savraz [at] gmail.com)
 */
public class StringAdapter extends TypeAdapter<String>
{
	@Override
	public String unmarshall(String from)
	{
		return from;
	}
}
