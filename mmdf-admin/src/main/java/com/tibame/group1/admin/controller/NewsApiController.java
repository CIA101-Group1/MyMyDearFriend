package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.service.NewsService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.NewsAddReqDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.NewsEntity;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsApiController {

    @Autowired NewsService newsService;

    @GetMapping("/news")
    public ResDTO<List<NewsEntity>> findAll() {
        ResDTO<List<NewsEntity>> res = new ResDTO<>();
        res.setData(newsService.findAll());
        return res;
    }

    @GetMapping("/news/{newsId}")
    public ResDTO<NewsEntity> findById(@PathVariable(name = "newsId") Integer newsId) {
        ResDTO<NewsEntity> res = new ResDTO<>();
        res.setData(newsService.findById(newsId));
        return res;
    }

    @PostMapping("/news")
    public @ResponseBody ResDTO<?> add(@Valid NewsAddReqDTO req)
            throws IOException, CheckRequestErrorException {

        if (req.getImage().isEmpty()) {
            throw new CheckRequestErrorException("最新消息圖片：請上傳1張圖片");
        }
        if (req.getImage().getSize() > 5 * 1024 * 1024) {
            throw new CheckRequestErrorException("最新消息圖片：檔案必須小於5MB");
        }
        newsService.add(req);
        return new ResDTO<>();
    }

    @PutMapping("/news/{newsId}")
    public @ResponseBody ResDTO<?> update(
            @PathVariable(name = "newsId") Integer newsId, @Valid NewsAddReqDTO req)
            throws IOException, CheckRequestErrorException {

        if (req.getImage().isEmpty()) {
            throw new CheckRequestErrorException("最新消息圖片：請上傳1張圖片");
        }
        if (req.getImage().getSize() > 5 * 1024 * 1024) {
            throw new CheckRequestErrorException("最新消息圖片：檔案必須小於5MB");
        }
        newsService.update(newsId, req);
        return new ResDTO<>();
    }

    // @PutMapping("/news/{newsId}")
    // public @ResponseBody ResDTO<?> updateStatus(
    //         @PathVariable("newsId") Integer newsId, @RequestParam("newStatus") Integer newStatus)
    //         throws CheckRequestErrorException {
    //
    //     newsService.updateStatus(newsId, newStatus);
    //     return new ResDTO<>();
    // }
}
