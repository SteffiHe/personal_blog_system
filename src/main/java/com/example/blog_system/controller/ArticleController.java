package com.example.blog_system.controller;

import com.example.blog_system.dto.ArticleDTO;
import com.example.blog_system.entity.Article;
import com.example.blog_system.result.Result;
import com.example.blog_system.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * Retrieves all articles
     * @return a list of all articles
     */
    @GetMapping("/getAllArticles")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    /**
     * Retrieves all articles with authorname
     * @return a list of all articles authorname
     */
    @GetMapping("/getAllArticlesWithAuthorname")
    public ResponseEntity<List<Article>> getAllArticlesWithAuthorname() {
        List<Article> articles = articleService.getAllArticlesWithAuthorname();
        return ResponseEntity.ok(articles);
    }

    /**
     * Retrieves all articles with authorname DTO
     * @return a list of all articles authorname DTO
     */
    @GetMapping("/getAllArticlesWithAuthornameDTO")
    public ResponseEntity<List<ArticleDTO>> getAllArticlesWithAuthornameDTO() {
        List<ArticleDTO> articles = articleService.getAllArticlesWithAuthornameDTO();
        return ResponseEntity.ok(articles);
    }

    /**
     * Retrieves all articles with optional sorting
     * @param sortBy sorting criteria (title, createTime, author)
     * @return a list of sorted articles
     */
    @RequestMapping(value = "/getAllArticlesSorted",method = RequestMethod.GET)
    public ResponseEntity<List<Article>> getAllArticlesSorted(@RequestParam(defaultValue = "createTime") String sortBy) {
        List<Article> sortedArticles = articleService.getAllArticlesSorted(sortBy);
        return ResponseEntity.ok(sortedArticles);
    }


    /**
     * Retrieves articles containing a specified keyword
     * in their title, content or author
     * @param keyword keyword to search for
     * @return a list of matching articles
     */
    @RequestMapping(value = "/getArticleByKeyword",method = RequestMethod.GET)
    public ResponseEntity<List<Article>> getArticleByKeyword(@RequestParam String keyword) {
        List<Article> articles = articleService.getArticleByKeyword(keyword);
        return ResponseEntity.ok(articles);
    }

    /**
     * Inserts a new article
     * @param article article to insert
     * @return inserted article
     */
    @RequestMapping(value = "/insertArticle",method = RequestMethod.POST)
    public ResponseEntity<Article> insertArticle(@RequestBody Article article) {
        Article savedArticle = articleService.insertArticle(article);
        return ResponseEntity.ok(savedArticle);
    }

    /**
     * Deletes an article by its ID
     * @param id id of the article to delete
     */
    @RequestMapping(path = "/byId/{id}",method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable String id) {

        articleService.deleteArticle(id);
    }


}
