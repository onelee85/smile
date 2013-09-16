package com.smile.admin

import com.smile.admin.web.utils.HttpSessionConstant
import groovy.transform.CompileStatic
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.View

import javax.servlet.http.HttpServletRequest

@Controller
@CompileStatic
public class AdminController {

    @RequestMapping(value = "/public/login_page")
    public ModelAndView login_page() {
        ModelAndView mv = new ModelAndView("login_page");
        return mv;
    }

    @RequestMapping(value = "/public/login",method=RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request){
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        request.getSession().setAttribute("user",name)
        String url = request.getSession().getAttribute(HttpSessionConstant.REDIRECT_URL);
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }
    @RequestMapping(value = "/admin/member_list")
    public ModelAndView memberList() {
        ModelAndView mv = new ModelAndView("/admin/member_list");
        mv.addObject("name", "John");
        return mv;
    }


}
