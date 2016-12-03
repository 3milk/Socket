package test;

import java.lang.annotation.Annotation;

import dev.factory.AnnotationParser.Controller;
import dev.factory.AnnotationParser.RequestMapping;
import framework.ModelAndView;



@Controller
public class test {
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView mdv) {
		ModelAndView mav=mdv;
		mav.setViewName("index");
		return mav;
	}
	@RequestMapping("/hello")
	public ModelAndView  hello(ModelAndView mdv) {
		ModelAndView mav=mdv;
		// TODO Auto-generated constructor stub
		mav.setViewName("test");
		mav.addObject("name", mav.getMap("name"));
		mav.addObject("pas", mav.getMap("pas"));
		return mav;
	}
	@RequestMapping("/hello2")
	public ModelAndView  hello2(ModelAndView mdv) {
		ModelAndView mav =mdv;
		// TODO Auto-generated constructor stub
		mav.setViewName("test");
		return mav;
	}
}
