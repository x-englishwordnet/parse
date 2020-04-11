package io.github.xenglishwordnet.pojos;

import java.util.function.Function;

/**
 * Lexical Relation (a lexical relation is an extended semantical relation)
 *
 * @author Bernard Bou
 */
public class LexRelation extends SemRelation
{
	public final Lemma fromWord;

	private final LemmaRef toWord;

	public LexRelation(final RelationType type, final SynsetId fromSynsetId, final SynsetId toSynsetId, final Lemma fromWord, final LemmaRef toWord)
	{
		super(type, fromSynsetId, toSynsetId);
		this.fromWord = fromWord;
		this.toWord = toWord;
	}

	@Override
	public String toString()
	{
		return this.type.getName() + "-[" + this.fromWord + "]:" + this.toWord;
	}

	public Lemma resolveToWord(final Function<SynsetId, CoreSynset> f)
	{
		return this.toWord.resolve(f);
	}
}
