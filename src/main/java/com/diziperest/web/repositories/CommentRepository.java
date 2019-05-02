package com.diziperest.web.repositories;

import com.diziperest.web.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByHashtag_IdAndSeries_Id(Long hashtagId, Long serieId);

    List<Comment> findByHashtag_Id(Long hashtagId);
}
