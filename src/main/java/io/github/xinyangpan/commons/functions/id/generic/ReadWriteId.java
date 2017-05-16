package io.github.xinyangpan.commons.functions.id.generic;

public interface ReadWriteId<T> extends ReadId<T> {

	void setId(T id);

}
