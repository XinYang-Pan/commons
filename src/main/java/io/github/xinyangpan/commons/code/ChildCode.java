package io.github.xinyangpan.commons.code;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ChildCode extends Code {
	protected Long parentId;

	@Override
	public String toString() {
		return String.format("%s [parentId=%s, id=%s, name=%s, value=%s, type=%s, description=%s, sort=%s]", this.getClass().getSimpleName(), parentId, id, name, value, type, description, sort);
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
