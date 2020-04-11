package io.github.xenglishwordnet.parse;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class TestSensekey
{
	// go_to_the_dogs%2:30:00:: 00207821 0 0
	// half-size%5:00:00:small:00 01375781 0 0

	@Test
	public void mainTestXX_sk_escape() throws IOException
	{
		String[] lemmas = { "one", "two%three", "two%%three", "two%%%%three", "two%%%%%three", "two%", "%three", "two%%", "%%three", "normal", "two:three",
				"two::three", "two::::three", "two:::::three", "two:", ":three", "two::", "::three" };
		List<String> heads = new ArrayList<>(Arrays.asList(lemmas));
		heads.add(0, "");
		String lexid = "1:22:33";
		String headid = "44";

		for (String lemma : lemmas)
		{
			for (String head : heads)
			{
				String sk = encode(lemma, lexid, head, head.isEmpty() ? "" : headid);
				System.out.printf("%s%n", sk);

				String[] decoded = decode(sk);
				assertEquals(decoded.length, 6);
				assertEquals(decoded[0], lemma);
				assertEquals(decoded[1], "1");
				assertEquals(decoded[2], "22");
				assertEquals(decoded[3], "33");
				assertEquals(decoded[4], head);
				if (head.isEmpty())
					assertEquals(decoded[5], "");
				else
					assertEquals(decoded[5], headid);
			}
		}
		System.out.println("Done");
	}

	static private Pattern patternBreak = Pattern.compile("(?<!\\u005C)%");

	private static String[] decode(String sk)
	{
		String[] fields = new String[6];
		String[] fields1 = patternBreak.split(sk);
		assert fields1.length == 2;
		// lemma
		fields[0] = fields1[0].replace("\\%", "%");
		// lexsense
		String lexSense = fields1[1].replace("\\%", "%");
		// String typeFileLexid = lexSense.substring(0, 8);
		fields[1] = lexSense.substring(0, 1);
		fields[2] = lexSense.substring(2, 4);
		fields[3] = lexSense.substring(5, 7);
		int last = lexSense.lastIndexOf(':');
		fields[4] = lexSense.substring(8, last);
		fields[5] = lexSense.substring(last + 1);
		return fields;
	}

	private static String encode(String lemma, String lexid, String head, String headid)
	{
		return lemma.replace("%", "\\%") + '%' + lexid + ':' + head.replace("%", "\\%") + ':' + headid;
	}
}
