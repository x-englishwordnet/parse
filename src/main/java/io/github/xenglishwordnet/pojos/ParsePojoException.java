package io.github.xenglishwordnet.pojos;

public class ParsePojoException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ParsePojoException(String message)
	{
		super(message);
	}

	public ParsePojoException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ParsePojoException(Throwable cause)
	{
		super(cause);
	}
}
