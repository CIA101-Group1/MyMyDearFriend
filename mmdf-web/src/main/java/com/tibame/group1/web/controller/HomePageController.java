package com.tibame.group1.web.controller;

import com.tibame.group1.db.entity.NewsEntity;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.web.service.NewsService;
import com.tibame.group1.web.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomePageController {

    @Autowired ProductService productService;

    @Autowired NewsService newsService;

    @GetMapping({"/", "/home"})
    public String getHomePage(Model model) {
        List<NewsEntity> newsList = newsService.findAllActiveNews();
        model.addAttribute("newsList", newsList);

        Page<ProductEntity> productPage = productService.productGetAll(PageRequest.of(0, 4));
        model.addAttribute("productPage", productPage);
        return "/home";
    }

    @GetMapping({"/news/{newsId}"})
    public String getOneNewsPage(@PathVariable("newsId") Integer newsId, Model model) {
        NewsEntity news = newsService.findNewsById(newsId);
        model.addAttribute("news", news);
        return "/news/news-detail";
    }

    @GetMapping("/example")
    public String hello() {
        return "example";
    }
}
