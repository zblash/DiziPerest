package com.diziperest.web.controllers;

import com.diziperest.web.dtos.CommentDto;
import com.diziperest.web.models.Comment;
import com.diziperest.web.models.CustomPrincipal;
import com.diziperest.web.models.Hashtag;
import com.diziperest.web.models.Series;
import com.diziperest.web.repositories.CommentRepository;
import com.diziperest.web.repositories.HashtagRepository;
import com.diziperest.web.repositories.SeriesRepository;
import com.diziperest.web.services.concretes.SeriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class CommentController {
    @Autowired
    private HashtagRepository hashtagRepository;
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private CommentRepository commentRepository;

    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    @PostMapping("/addcomment")
    public String seriesPage(Authentication authentication, CommentDto commentDto, BindingResult result) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        Series serie = seriesRepository.findById(commentDto.getSerie()).orElseThrow(RuntimeException::new);

        String REG_EX_TAG = ".*?\\s(#\\w+).*?";
        Pattern tagMatcher = Pattern.compile(REG_EX_TAG);
        Matcher m = tagMatcher.matcher(commentDto.getComment());
        if (m.find())
        {
            Hashtag hashtag = hashtagRepository.findByName(m.group(1)).orElseGet(() -> {
                Hashtag newHashtag = new Hashtag();
                newHashtag.setName(m.group(1).substring(1));
                newHashtag.addUser(principal.getUser());
                return hashtagRepository.save(newHashtag);
            });
            serie.addHashtag(hashtag);
            seriesRepository.save(serie);
            Comment comment = new Comment();
            comment.setComment(commentDto.getComment());
            comment.setSeries(serie);
            comment.setHashtag(hashtag);
            comment.setUser(principal.getUser());
            commentRepository.save(comment);
        }
        return "redirect:/diziler/"+serie.getId();
    }
}
