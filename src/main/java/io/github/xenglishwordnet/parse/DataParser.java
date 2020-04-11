package io.github.xenglishwordnet.parse;

import io.github.xenglishwordnet.pojos.ParsePojoException;
import io.github.xenglishwordnet.pojos.Synset;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Synset Parser data.{noun|verb|adj|adv}
 *
 * @author Bernard Bou
 */
public class DataParser
{
	private static final boolean THROW = false;

	private static final boolean DUMP = false;

	public static void main(String[] args) throws ParsePojoException, IOException
	{
		// Timing
		final long startTime = System.currentTimeMillis();

		// Input
		String dir = args[0];

		// Process
		for (final String posName : new String[] { "noun", "verb", "adj", "adv" })
		{
			parseSynsets(dir, posName);
		}

		// Timing
		final long endTime = System.currentTimeMillis();
		System.err.println("Total execution time: " + (endTime - startTime) / 1000 + "s");
	}

	static void parseSynsets(final String dir, final String posName) throws ParsePojoException, IOException
	{
		System.out.println("* Synsets " + posName);

		final boolean isAdj = posName.equals("adj");

		// iterate on lines
		final File file = new File(dir, "data." + posName);
		try (RandomAccessFile raFile = new RandomAccessFile(file, "r"))
		{
			raFile.seek(0);

			// iterate on lines
			int offsetErrorCount = 0;
			int parseErrorCount = 0;
			long synsetCount = 0;
			int lineCount = 0;

			String rawLine;
			long fileOffset = raFile.getFilePointer();
			for (; (rawLine = raFile.readLine()) != null; fileOffset = raFile.getFilePointer())
			{
				lineCount++;
				if (rawLine.isEmpty() || rawLine.charAt(0) == ' ')
				{
					continue;
				}

				// decode
				String line = new String(rawLine.getBytes(Flags.charSet));

				// split into fields
				final String[] lineFields = line.split("\\s+");

				// read offset
				long readOffset = Long.parseLong(lineFields[0]);
				if (fileOffset != readOffset)
				{
					System.out.println(posName + ';' + lineCount + " offset=" + fileOffset + " line=[" + line + "]");
					offsetErrorCount++;
					continue;
				}

				// read
				try
				{
					Synset synset = parseSynset(line, isAdj);
					synsetCount++;
					if (DUMP)
						System.out.println(synset);
				}
				catch (final ParsePojoException e)
				{
					parseErrorCount++;
					System.err.printf("%n%s:%d offset=%08d line=[%s] except=%s", file.getName(), lineCount, fileOffset, line, e.getMessage());
					if (THROW)
						throw e;
				}
			}
			System.out.println("lines          	" + lineCount);
			System.out.println("offset errors  	" + offsetErrorCount);
			System.out.println("parse errors   	" + parseErrorCount);
			System.out.println("parse successes " + synsetCount);
		}
	}

	private static Synset parseSynset(String line, boolean isAdj) throws ParsePojoException
	{
		return Synset.parseSynset(line, isAdj);
	}
}
