package com.zm.controller;

import com.zm.model.Question;
import com.zm.model.User;
import com.zm.model.ViewObject;
import com.zm.service.questionService;
import com.zm.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellen on 2017/5/20.
 */
@Controller
public class homeController {
    @Autowired
    private userService userService;

    @Autowired
    private questionService questionService;

    @RequestMapping(path = {"/index/{userId}"})
    public String userIndex(Model model,
                            @PathVariable("userId") Integer userId) {
        int id = 0;
        if (userId != null) {
            id = userId;
        }
        List<ViewObject> vos = getList(id, 0, 10);
        model.addAttribute("vos", vos);
        return "/index";
    }

    @RequestMapping(path = {"/index", "/"})
    public String index(Model model) {
        List<ViewObject> vos = getList(0, 0, 10);
        model.addAttribute("vos", vos);
        return "/index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String user(Model model,
                       @PathVariable(value = "userId") int userId) {
        model.addAttribute("vos", getList(userId,0,10));
        return "/index";
    }

    private List<ViewObject> getList(int userId, int offset, int limit) {
        List<Question> latestQuestion = questionService.findLatestQuestion(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : latestQuestion) {
            ViewObject vo = new ViewObject();
            User user = userService.selectUserBuId(question.getUserId());
            vo.set("user", user);
            vo.set("question", question);
            vos.add(vo);
        }
        return vos;
    }

}
