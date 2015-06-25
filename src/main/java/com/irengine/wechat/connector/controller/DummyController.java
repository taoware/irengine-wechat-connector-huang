package com.irengine.wechat.connector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**虚拟?*/
@Controller
public class DummyController {

    @RequestMapping("/dummy")
    public String dummy(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "dummy";
    }
}