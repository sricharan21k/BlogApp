package com.app.blogApp.articles;

import com.app.blogApp.articles.dtos.CreateArticleRequest;
import com.app.blogApp.articles.dtos.UpdateArticleRequest;
import com.app.blogApp.users.UserRepository;
import com.app.blogApp.users.UserService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticle(String slug) {
        return articleRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));
    }

    public Article createArticle(CreateArticleRequest a, Long authorId) {

        var author = userRepository.findById(authorId).orElseThrow(() -> new UserService.UserNotFoundException(authorId));
        var article = Article.builder()
                .title(a.getTitle())
                .slug(a.getTitle().toLowerCase().replace("\\s+", "-"))
                .subtitle(a.getSubtitle())
                .body(a.getBody())
                .author(author)
                .build();
        return articleRepository.save(article);
    }

    public Article updateArticle(UpdateArticleRequest a, Long articleId) {

        var article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if (a.getTitle() != null) {
            article.setTitle(a.getTitle());
            article.setSlug(a.getTitle().toLowerCase().replace("\\s+", "-"));
        }
        if (a.getBody() != null) {
            article.setSubtitle(a.getSubtitle());
        }
        if (a.getSubtitle() != null) {
            article.setSubtitle(a.getSubtitle());
        }
        return articleRepository.save(article);
    }


    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article " + slug + " not found");
        }

        public ArticleNotFoundException(Long id) {
            super("Article with id " + id + " not found");
        }
    }
}
