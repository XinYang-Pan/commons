package io.github.xinyangpan.commons.tostring;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Lists;

public class ToStringUtils {
	private static final Comparator<Field> COMPARATOR = new Comparator<Field>() {
		@Override
		public int compare(Field o1, Field o2) {
			return this.getValue(o1) - this.getValue(o2);
		}
		
		private int getValue(Field f) {
			if (isIterable(f.getType())) {
				return 2;
			} else if (isNotPrimeClass(f.getType())) {
				return 1;
			}
			return 0;
		}
	};

	public static ToStringWrapper wellFormatWrapper(Object obj) {
		return new ToStringWrapper(obj) {
			@Override
			public String notNulltoString(Object target) {
				return wellFormat(target);
			}
		};
	}
	
	public static String wellFormat(Object obj) {
		if (obj == null) {
			return "null";
		}
		try {
			if (obj instanceof Iterable<?>) {
				Iterable<?> iterable = (Iterable<?>)obj;
				Iterator<?> iterator = iterable.iterator();
				StringBuilder sb = new StringBuilder();
				while (iterator.hasNext()) {
					sb.append(wellFormat(iterator.next(), 1));
					if (iterator.hasNext()) {
						sb.append(System.lineSeparator());
					}
				}
				return sb.toString();
			}
			// None Iterable
			return wellFormat(obj, 1);
		} catch (Throwable e) {
			e.printStackTrace();
			return String.format("Unable to well format this object. %s - %s", e.getClass().getName(), e.getMessage());
		}
	}
	
	private static String wellFormat(Object obj, int level) {
		List<Field> fields = Lists.newArrayList();
		Class<?> objectClass = obj.getClass();
		Class<?> temp = objectClass;
		while (temp != Object.class) {
			fields.addAll(Lists.newArrayList(temp.getDeclaredFields()));
			temp = temp.getSuperclass();
		}
		Collections.sort(fields, COMPARATOR);
		// 
		StringBuilder sb = new StringBuilder();
		sb.append(obj.getClass().getSimpleName()).append(" [");
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			field.setAccessible(true);
			Object fieldValue = ReflectionUtils.getField(field, obj);
			// 
			if (fieldValue == null) {
				sb.append(String.format("%s=%s", field.getName(), fieldValue));
			} else if (isIterable(field.getType())) {
				sb.append(System.lineSeparator()).append(repeat(level*2-1)).append(String.format("%s={", field.getName()));
				// Iterable
				Iterable<?> iterableFieldValue = (Iterable<?>)fieldValue;
				Iterator<?> fieldValueIterator = iterableFieldValue.iterator();
				while (fieldValueIterator.hasNext()) {
					Object fieldValueEach = fieldValueIterator.next();
					if (!fieldValueIterator.hasNext()) {
						sb.append(",");
					}
					sb.append(System.lineSeparator()).append(repeat(level*2)).append(wellFormat(fieldValueEach, level+1));
				}
				sb.append(System.lineSeparator()).append(repeat(level*2-1)).append("}");
				sb.append(System.lineSeparator()).append(repeat(level*2-2));
			} else if (isNotPrimeClass(field.getType())) {
				// not prime class
				sb.append(String.format("%s=%s", field.getName(), wellFormat(fieldValue, level)));;
			} else {
				sb.append(String.format("%s=%s", field.getName(), fieldValue));
			}
			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	private static String repeat(int level) {
		return StringUtils.repeat("\t", level);
	}

	private static boolean isIterable(Class<?> objectClass) {
		return Iterable.class.isAssignableFrom(objectClass);
	}

	private static boolean isNotPrimeClass(Class<?> objectClass) {
		if(ClassUtils.isPrimitiveOrWrapper(objectClass)) {
			return false;
		}
		if(objectClass.isArray()) {
			return false;
		}
		Package package1 = objectClass.getPackage();
		if (package1 == null) {
			System.err.println("None package for "+objectClass);
		}
		String packageName = package1.getName();
		return !packageName.startsWith("java");
	}
	
}