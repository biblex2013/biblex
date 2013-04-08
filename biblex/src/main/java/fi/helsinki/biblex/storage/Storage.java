package fi.helsinki.biblex.storage;

import fi.helsinki.biblex.domain.BibTexEntry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;

/**
 */

public abstract class Storage implements Iterable<BibTexEntry> {
	public Storage() {}

	public abstract void open(String url) throws Exception;
	public abstract void create(String url) throws Exception;

	public abstract void close();

	public abstract long add(BibTexEntry entry) throws Exception;
	public abstract boolean delete(long uid) throws Exception;
	public abstract boolean update(long uid, BibTexEntry entry) throws Exception;

	public abstract BibTexEntry get(String name);

	public abstract Iterable<BibTexEntry> search(String key, String value);
	public abstract Iterable<BibTexEntry> search(Iterable<Map.Entry<String, String>> constraints);
}
