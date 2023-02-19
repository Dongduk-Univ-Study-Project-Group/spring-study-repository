package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PathVariableController {

	/** 화면 표시 */
	@GetMapping("show")
	public String showView() {
		// 반환값으로 뷰 이름을 돌려줌
		return "show";
	}
	/** 링크 처리 */
	@GetMapping("/function/{no}")
	public String selectFunction(@PathVariable Integer no) {
	// 뷰명 초기화
		String view = null;
		switch (no) {
			case 1:
				view = "pathvariable/function1";
				break;
			case 2:
				view = "pathvariable/function2";
				break;
			case 3:
				view = "pathvariable/function3";
				break;
		}
		// 반환값으로 뷰 이름을 돌려줌
		return view;
	}

	/** 버튼 A 클릭 처리 */
	@PostMapping(value = "send", params = "a")
	public String showAView() {
		// 반환값으로 뷰 이름을 돌려줌
		return "submit/a";
	}
	/** 버튼 B 클릭 처리 */
	@PostMapping(value = "send", params = "b")
	public String showBView() {
		// 반환값으로 뷰 이름을 돌려줌
		return "submit/b";
	}
	/** 버튼 C 클릭 처리 */
	@PostMapping(value = "send", params = "c")
	public String showCView() {
		// 반환값으로 뷰 이름을 돌려줌
		return "submit/c";
	}
}