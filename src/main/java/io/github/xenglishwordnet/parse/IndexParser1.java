package io.github.xenglishwordnet.parse;

import io.github.xenglishwordnet.pojos.CoreIndex;
import io.github.xenglishwordnet.pojos.ParsePojoException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class IndexParser1
{
	public static void main(String[] args) throws IOException, ParsePojoException
	{
		// Timing
		final long startTime = System.currentTimeMillis();

		// Input
		String dir = args[0];
		String posName = args[1];
		String target = args[2];
		final boolean isAdj = posName.equals("adj");

		// Process
		read(dir, posName, target);

		// Timing
		final long endTime = System.currentTimeMillis();
		System.err.println("Total execution time: " + (endTime - startTime) / 1000 + "s");
	}

	public static void read(final String dir, final String posName, final String target) throws IOException
	{
		final File file = new File(dir, "index." + posName);
		try (final BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				if (line.contains(target))
				{
					System.out.println(line);
					CoreIndex index = null;
					try
					{
						index = parseIndexLine(line);
						System.out.println(index);
					}
					catch (ParsePojoException e)
					{
						System.err.printf("%s cause:%s%n", e.getMessage(), e.getCause());
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static CoreIndex parseIndexLine(String line) throws ParsePojoException
	{
		return CoreIndex.parseCoreIndex(line);
	}
}
