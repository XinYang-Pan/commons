package io.github.xinyangpan.commons.tostring;

import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

public class IterableMultilineToString<T> {
	private final Iterable<T> iterable;
	private final String prefix;

	public IterableMultilineToString(Iterable<T> iterable) {
		this(iterable, null);
	}

	public IterableMultilineToString(Iterable<T> iterable, String prefix) {
		super();
		this.iterable = Objects.requireNonNull(iterable);
		this.prefix = ObjectUtils.firstNonNull(prefix, "");
	}

	@Override
	public String toString() {
		// 
		StringBuilder sb = new StringBuilder();
		Iterator<T> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			sb.append(prefix).append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}

	public static String toString(Iterable<?> iterable, String prefix) {
		return new IterableMultilineToString<>(iterable, prefix).toString();
	}

	public static String toString(Iterable<?> iterable) {
		return toString(iterable, null);
	}

}