package io.github.xinyangpan.commons.code;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestCodeController {
	@Autowired
	private CodeService codeService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/getByParentId")
	public List<Code> getByParentId(@RequestParam("codeType") String codeType, @RequestParam("parentId") Long parentId) {
		Class clazz = codeService.getCodeType(codeType);
		return codeService.getCodesByParentId(clazz, parentId);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/{codeType}")
	public List<Code> list(@PathVariable("codeType") String codeType) {
		Class<? extends Code> clazz = codeService.getCodeType(codeType);
		return (List<Code>) codeService.getCodes(clazz);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/mapByParentId")
	public Map<Long, List<ChildCode>> mapByParentId(@RequestParam("codeType") String codeType) {
		Class clazz = codeService.getCodeType(codeType);
		return codeService.getCodesMapByParentId(clazz);
	}

}
