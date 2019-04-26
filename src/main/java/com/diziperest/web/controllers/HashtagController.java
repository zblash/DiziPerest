package com.diziperest.web.controllers;

import com.diziperest.web.dtos.CommentDto;
import com.diziperest.web.models.Series;
import com.diziperest.web.repositories.HashtagRepository;
import com.diziperest.web.repositories.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HashtagController {

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @GetMapping("/diziler/{id}")
    public String seriesPage(@PathVariable Long id, Model model) {
        Series serie = seriesRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("serie",serie);
        model.addAttribute("comment", new CommentDto());
        return "series/detail";
    }
}
