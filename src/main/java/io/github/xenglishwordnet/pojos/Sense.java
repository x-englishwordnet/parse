package io.github.xenglishwordnet.pojos;

public class Sense extends CoreSense
{
	public final LexId lexId;

	public final TagCnt tagCnt;

	public Sense(final SynsetId synsetId, final Lemma lemma, final int sensenum, final Sensekey sensekey, final LexId lexId, final TagCnt tagCnt)
	{
		super(synsetId, lemma, sensenum, sensekey);
		this.lexId = lexId;
		this.tagCnt = tagCnt;
	}

	/**
	 * Parse sense from line
	 * 
	 * @param line
	 *            line
	 * @return sense
	 * @throws ParsePojoException
	 *             parse exception
	 */
	public static Sense parseSense(final String line) throws ParsePojoException
	{
		try
		{
			// line from index.sense
			// read line into fields
			// [0] sensekey
			// [1] synset offset
			// [2] sense number
			// [3] tag count
			final String[] fields = line.split("\\s+");

			// core fields
			final Sensekey sensekey = Sensekey.parseSensekey(fields[0]);
			final Pos pos = sensekey.getPos();
			final Lemma lemma = sensekey.getLemma();
			final int sensenum = Integer.parseInt(fields[2]);
			final SynsetId synsetId = new SynsetId(pos, Long.parseLong(fields[1]));

			// parse tag/lexid
			final TagCnt tagCnt = TagCnt.parseTagCnt(fields[3]);
			final LexId lexId = LexId.make(sensekey);

			return new Sense(synsetId, lemma, sensenum, sensekey, lexId, tagCnt);
		}
		catch (Exception e)
		{
			throw new ParsePojoException(e);
		}
	}

	@Override
	public String toString()
	{
		return super.toString() + " l" + this.lexId + " t" + this.tagCnt;
	}
}
