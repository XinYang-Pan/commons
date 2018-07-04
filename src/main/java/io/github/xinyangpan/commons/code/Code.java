package io.github.xinyangpan.commons.code;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Code {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;
	protected String name;
	protected String value;
	protected String type;
	protected String description;
	protected Integer sort;

	@Override
	public String toString() {
		return String.format("%s [id=%s, name=%s, value=%s, type=%s, description=%s, sort=%s]", this.getClass().getSimpleName(), id, name, value, type, description, sort);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
