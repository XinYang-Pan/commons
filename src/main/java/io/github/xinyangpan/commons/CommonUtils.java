package io.github.xinyangpan.commons;

import java.lang.reflect.Type;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;

public class CommonUtils {

	/* Sample
	@SuppressWarnings("serial")
	private final Class<T> parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});
	*/
	@SuppressWarnings({ "unchecked"})
	public static <T> Class<T> getParameterizedClass(TypeToken<T> typeToken) {
		Type type = typeToken.getType();
		//
		Preconditions.checkArgument(type instanceof Class<?>);
		Class<T> clazz = (Class<T>) type;
		return clazz;
	}
	
	// this supports nested beginChar and endChar, will use most outer beginChar and endChar
	public static ContentPiece splitContent(String string, char beginChar, char endChar) {
		if (Strings.isNullOrEmpty(string)) {
			return new ContentPiece(null, null, null);
		}
		char[] charArray = string.toCharArray();
		int count = 0;
		Integer startPos = null;
		Integer endPos = null;
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (c == beginChar) {
				count++;
				if (startPos == null) {
					startPos = i;
				}
			} else if (c == endChar) {
				if (count > 0) {
					count--;
					if (count == 0 && endPos == null) {
						endPos = i;
						break;
					}
				}
			}
		}
		if (startPos == null || endPos == null) {
			return new ContentPiece(null, string, null);
		}
		//
		String beforeContent = string.substring(0, startPos);
		String content = string.substring(startPos + 1, endPos);
		String afterContent = string.substring(endPos + 1, string.length());
		return new ContentPiece(content, beforeContent, afterContent);
	}

}
