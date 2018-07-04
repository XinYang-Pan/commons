package io.github.xinyangpan.commons.code;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * should be extended to put codes into cache via putIntoMap
 * 
 * @author panxy
 *
 */
public class CodeService {
	// for rest use, should be initialized
	private Map<String, Class<? extends Code>> codeTypeName2CodeList = new HashMap<>();
	private Map<Class<?>, List<?>> codeType2CodeList = new HashMap<>();
	private Map<Class<?>, Map<Long, ?>> codeType2CodeId2Code = new HashMap<>();
	
	public Class<? extends Code> getCodeType(String codeTypeName) {
		return Objects.requireNonNull(codeTypeName2CodeList.get(codeTypeName));
	}
	
	public <T extends Code> void putIntoMap(Class<T> clazz, Iterable<T> elements) {
		// 
		List<T> list = Lists.newArrayList(elements);
		list.sort(comparing(Code::getSort, nullsLast(naturalOrder())).thenComparing(Code::getId));
		codeType2CodeList.put(clazz, list);
		// 
		Map<Long, T> codeMap = Maps.newHashMap();
		for (T t : list) {
			codeMap.put(t.getId(), t);
		}
		codeType2CodeId2Code.put(clazz, codeMap);
	}

	@SuppressWarnings("unchecked")
	public <T extends Code> List<T> getCodes(Class<T> clazz) {
		return (List<T>) codeType2CodeList.get(clazz);
	}

	public <T extends ChildCode> Map<Long, List<T>> getCodesMapByParentId(Class<T> clazz) {
		List<T> codes = this.getCodes(clazz);
		return codes.stream().collect(Collectors.groupingBy(ChildCode::getParentId));
	}

	public <T extends ChildCode> List<T> getCodesByParentId(Class<T> clazz, Long parentId) {
		return this.getCodesMapByParentId(clazz).get(parentId);
	}

	@SuppressWarnings("unchecked")
	public <T extends Code> T getCode(Class<T> clazz, long id) {
		return (T) codeType2CodeId2Code.get(clazz).get(id);
	}

}
