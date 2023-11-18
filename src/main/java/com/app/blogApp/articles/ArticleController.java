package com.app.blogApp.articles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService articleService;

    @GetMapping
    public String getArticles(){
        return "articles";
    }
}
