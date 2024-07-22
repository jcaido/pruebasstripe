package com.jcaido.pruebastripe.service;

import com.jcaido.pruebastripe.model.Article;
import com.jcaido.pruebastripe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id_article) {
        return articleRepository.findById(id_article).orElseThrow();
    }

    public Boolean existId(Long id_article) {
        return articleRepository.existsById(id_article);
    }
}
