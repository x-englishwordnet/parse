package io.github.xenglishwordnet.parse;

import java.io.IOException;

import org.junit.Test;

import io.github.xenglishwordnet.pojos.ParsePojoException;

public class TestParse
{
	@Test
	public void parse() throws IOException, ParsePojoException
	{
		String wnHome = System.getenv("WNHOMEXX" /* + File.separator + "dict" */);
		Parser.main(new String[] { wnHome });
	}

	@Test
	public void indexParse() throws IOException, ParsePojoException
	{
		String wnHome = System.getenv("WNHOMEXX" /* + File.separator + "dict" */);
		IndexParser.main(new String[] { wnHome });
	}

	@Test
	public void dataParse() throws IOException, ParsePojoException
	{
		String wnHome = System.getenv("WNHOMEXX" /* + File.separator + "dict" */);
		DataParser.main(new String[] { wnHome });
	}

	@Test
	public void senseParse() throws IOException, ParsePojoException
	{
		String wnHome = System.getenv("WNHOMEXX" /* + File.separator + "dict" */);
		SenseParser.main(new String[] { wnHome });
	}
}
