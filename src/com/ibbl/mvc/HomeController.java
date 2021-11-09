package com.ibbl.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home/")
public class HomeController {

	@RequestMapping(method = RequestMethod.GET)
	public String welcome(ModelMap model) {
		model.addAttribute("msg", "");
		return "login";
	}

	@RequestMapping(method = RequestMethod.GET, value = "logOut")
	public String login(ModelMap model, @RequestParam String msg, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session =  request.getSession();
		session.invalidate();
		model.addAttribute("msg", msg);
		return "login";
	}

}