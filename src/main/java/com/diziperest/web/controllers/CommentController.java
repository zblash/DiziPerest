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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
            Hashtag hashtag = hashtagRepository.findByName(m.group(1).substring(1)).orElseGet(() -> {
                Hashtag newHashtag = new Hashtag();
                newHashtag.setName(m.group(1).substring(1));
                newHashtag.addUser(principal.getUser());
                Hashtag saved = hashtagRepository.save(newHashtag);
                serie.addHashtag(saved);
                return saved;
            });

            seriesRepository.save(serie);
            Comment comment = new Comment();
            comment.setComment(commentDto.getComment());
            comment.setSeries(serie);
            comment.setHashtag(hashtag);
            comment.setUser(principal.getUser());

            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            String formattedDate = df.format(new Date());
            try {
                comment.setCreatedAt(df.parse(formattedDate));
            } catch (ParseException e) {
                throw new RuntimeException();
            }

            commentRepository.save(comment);
        }
        return "redirect:/diziler/"+serie.getId();
    }

    @ResponseBody
    @RequestMapping(value="/getcomments/{hashtagId}", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getComments(@PathVariable Long hashtagId, @RequestParam(value = "serieId", required=false) Long serieId){
       if (Optional.ofNullable(serieId).isPresent()){
           return ResponseEntity.ok(commentRepository.findByHashtag_IdAndSeries_Id(hashtagId,serieId));
       }
       return ResponseEntity.ok(commentRepository.findByHashtag_Id(hashtagId));
    }
}
