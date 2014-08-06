package org.lutra.unmarshaller;

/**
 * 06.08.2014 at 14:46
 * TypeAdapter of jdbc-unmarshaller project
 *
 * @author Dmitry V. (savraz [at] gmail.com)
 */
public abstract class TypeAdapter<ToType>
{
	protected TypeAdapter(){}
	public abstract ToType unmarshall(String from);
}
