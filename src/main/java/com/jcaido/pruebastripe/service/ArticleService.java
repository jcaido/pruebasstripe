package com.jcaido.pruebastripe.service;

import com.jcaido.pruebastripe.model.Article;
import com.jcaido.pruebastripe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

}
