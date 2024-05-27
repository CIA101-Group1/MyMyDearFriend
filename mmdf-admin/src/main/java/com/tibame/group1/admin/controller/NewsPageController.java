package com.tibame.group1.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NewsPageController {
    @GetMapping("/news/list")
    public String GetNewsListPage() {
        return "/news/news-list";
    }

    @GetMapping("/news/add")
    public String getNewsAddPage() {
        return "/news/news-add";
    }

    @GetMapping("/news/edit/{newsId}")
    public String getNewsEditPage(@PathVariable(name = "newsId") Integer newsId, Model model) {
        model.addAttribute("newsId", newsId);
        return "/news/news-edit";
    }
}
