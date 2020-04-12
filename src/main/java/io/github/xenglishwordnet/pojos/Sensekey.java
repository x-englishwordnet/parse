package io.github.xenglishwordnet.pojos;

import java.util.regex.Pattern;

/**
 * Sensekey
 *
 * @author Bernard Bou
 */
public class Sensekey
{
	private final String key;

	private final NormalizedString word; // may contain uppercase

	private final Pos pos;

	private final LexDomain lexDomain;

	private final int lexId;

	private final NormalizedString headWord; // or null

	private final int headLexId; // -1 for none

	/**
	 * Copy constructor
	 *
	 * @param other
	 *            other sensekey
	 */
	public Sensekey(final Sensekey other)
	{
		this.key = other.key;
		this.word = other.word;
		this.pos = other.pos;
		this.lexDomain = other.lexDomain;
		this.lexId = other.lexId;
		this.headWord = other.headWord;
		this.headLexId = other.headLexId;
	}

	/**
	 * Constructor
	 *
	 * @param word
	 *            word (may contain uppercase)
	 * @param pos
	 *            part of speech
	 * @param lexDomain
	 *            lex domain
	 * @param lexId
	 *            lexid
	 * @param headWord
	 *            head word
	 * @param headLexId
	 *            head lexid
	 * @param key
	 *            sensekey
	 */
	private Sensekey(final NormalizedString word, final Pos pos, final LexDomain lexDomain, final int lexId, final NormalizedString headWord,
			final int headLexId, final String key)
	{
		this.key = key.trim();
		this.word = word;
		this.pos = pos;
		this.lexDomain = lexDomain;
		this.lexId = lexId;
		this.headWord = headWord;
		this.headLexId = headLexId;
	}

	/**
	 * Parse sensekey from string
	 *
	 * @param str
	 *            string
	 * @return sensekey
	 * @throws ParsePojoException
	 *             parse exception
	 */
	public static Sensekey parseSensekey(final String str) throws ParsePojoException
	{
		if (str == null)
			return null;
		final String[] fields = decode(str);
		if (fields.length < 4)
			throw new ParsePojoException("Sensekey:" + str);
		try
		{
			final NormalizedString word = new NormalizedString(fields[0]);
			final Pos pos = Pos.fromIndex(Integer.parseInt(fields[1]));
			final LexDomain lexDomain = LexDomain.parseLexDomain(fields[2]);
			final int lexid = Integer.parseInt(fields[3]);
			final NormalizedString headWord = fields[4].isEmpty() ? null : new NormalizedString(fields[4]);
			final int headLexid = fields[5].isEmpty() ? -1 : Integer.parseInt(fields[5]);
			return new Sensekey(word, pos, lexDomain, lexid, headWord, headLexid, str);
		}
		catch (Exception e)
		{
			throw new ParsePojoException("Sensekey:" + str);
		}
	}

	//public static final String ESCAPED_PERCENT = "\\%";
	public static final String ESCAPED_PERCENT = "/";

	/**
	 * Decode sensekey string into fields
	 *
	 * @param skStr
	 *            sensekey string
	 * @return fields
	 * @return fields[0] lemma (not space-normalized)
	 * @return fields[1] pos
	 * @return fields[2] lexfile
	 * @return fields[3] lexid
	 * @return fields[4] head lemma (or "", not space-normalized)
	 * @return fields[5] head lexid (or "")
	 */
	public static String[] decode(String skStr)
	{
		String[] fields = new String[6];
		String[] fields1 = patternBreak.split(skStr);
		assert fields1.length == 2;
		// lemma
		fields[0] = fields1[0].replace(ESCAPED_PERCENT, "%");
		// lexsense
		String lexSense = fields1[1].replace(ESCAPED_PERCENT, "%");
		// String typeFileLexid = lexSense.substring(0, 8);
		fields[1] = lexSense.substring(0, 1); // pos
		fields[2] = lexSense.substring(2, 4); // lexfile
		fields[3] = lexSense.substring(5, 7); // lexid
		int last = lexSense.lastIndexOf(':');
		fields[4] = lexSense.substring(8, last); // head (or "")
		fields[5] = lexSense.substring(last + 1); // head lexid (or "")
		return fields;
	}

	public LexDomain getLexDomain()
	{
		return this.lexDomain;
	}

	public NormalizedString getWord()
	{
		return this.word;
	}

	public Lemma getLemma()
	{
		return new Lemma(this.word);
	}

	public int getLexId()
	{
		return this.lexId;
	}

	public Pos getPos()
	{
		return this.pos;
	}

	public String getKey()
	{
		return this.key;
	}

	public NormalizedString getHeadWord()
	{
		return this.headWord;
	}

	public int getHeadLexId()
	{
		return this.headLexId;
	}

	@Override
	public String toString()
	{
		return this.key;
	}

	public String toXString()
	{
		return "word=" + this.word + " lexid=" + this.lexId + " lexdomain=" + this.lexDomain + " pos=" + this.pos;
	}

	static private Pattern patternBreak = Pattern.compile("(?<!\\u005C)%");
}