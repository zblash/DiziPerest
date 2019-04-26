package com.diziperest.web.controllers;

import com.diziperest.web.models.Series;
import com.diziperest.web.repositories.SeriesRepository;
import com.diziperest.web.services.concretes.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/diziler")
public class SeriesController {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeriesService seriesService;
    @GetMapping
    public String index(@RequestParam(required = false) Integer page, Model model){
        int pageNumber = Optional.ofNullable(page).orElse(1);
        Page<Series> series = seriesRepository.findAll(PageRequest.of(pageNumber - 1,12));
        model.addAttribute("series", series);
        int totalPages = series.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "series/index";
    }

    @GetMapping("/ekle")
    public String addSerie(Model model){
        Series serie = new Series();
        model.addAttribute("serie", serie);
        return "series/create";
    }

    @PostMapping("/ekle")
    public String addSeriePost(@Valid @ModelAttribute Series serie, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("serie", serie);
            return "series/create";
        }

        seriesService.save(serie);
        return "redirect:/diziler";
    }
}
