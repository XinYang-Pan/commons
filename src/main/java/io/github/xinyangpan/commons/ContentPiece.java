package io.github.xinyangpan.commons;

public class ContentPiece {
	private String target;
	private String before;
	private String after;

	public ContentPiece() {
	}

	public ContentPiece(String target, String before, String after) {
		super();
		this.target = target;
		this.before = before;
		this.after = after;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContentPiece [target=");
		builder.append(target);
		builder.append(", before=");
		builder.append(before);
		builder.append(", after=");
		builder.append(after);
		builder.append("]");
		return builder.toString();
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

}
