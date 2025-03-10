package com.example.blog_system.service;

import com.example.blog_system.entity.Article;
import com.example.blog_system.entity.Tag;
import com.example.blog_system.repository.ArticleRepository;
import com.example.blog_system.repository.CategoryRepository;
import com.example.blog_system.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private TagRepository tagRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public void ArticleService(ArticleRepository articleRepository, TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all articles from the database
     * @return a list of all articles
     */
    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }


    /**
     * Retrieves a list of articles that contain a specified keyword
     * in their title, content or author's name
     * @param keyword keyword to search for in an article
     * @return a list of matching articles
     */
    public List<Article> getArticleByKeyword(String keyword) {
        return articleRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                keyword, keyword, keyword);
    }

    /**
     * Saves a new article or updates an existing article
     * If the article contains tags or a category, they are also saved or updated
     * @param article article to save or update
     * @return saved or updated article
     */
    public Article insertArticle(Article article) {
        // save or update tags
        if (article.getTags() != null) {
            List<Tag> savedTags = new ArrayList<>();
            for (Tag tag : article.getTags()) {
                savedTags.add(
                        tagRepository.findByNameIgnoreCase(tag.getName()).orElseGet(() -> tagRepository.save(tag))
                );
            }
            article.setTags(savedTags);
        }

        // save or update category
        if (article.getCategory() != null) {
            article.setCategory(
                    categoryRepository.findByNameIgnoreCase(article.getCategory().getName())
                    .orElseGet(() -> categoryRepository.save( article.getCategory()))
            );
        }

        // save or update article
        return articleRepository.save(article);
    }

    /**
     * Deletes an article by its ID
     * @param id id of an article to delete
     */
    @Override
    public void deleteArticle(String id) {
        articleRepository.deleteById(id);
    }


}
