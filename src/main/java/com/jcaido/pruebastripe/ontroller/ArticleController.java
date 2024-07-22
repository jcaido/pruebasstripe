package com.jcaido.pruebastripe.ontroller;

import com.jcaido.pruebastripe.model.Article;
import com.jcaido.pruebastripe.payload.Message;
import com.jcaido.pruebastripe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@CrossOrigin(origins = "*")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        return new ResponseEntity<>(articleService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/article/{id_article}")
    public ResponseEntity<Article> GetArticleById(@PathVariable Long id_article) {
        if (!articleService.existId(id_article))
            return new ResponseEntity(new Message("it doesn't exist"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(articleService.getArticleById(id_article), HttpStatus.OK);
    }
}

