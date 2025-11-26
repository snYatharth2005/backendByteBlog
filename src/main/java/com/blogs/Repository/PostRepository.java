package com.blogs.Repository;

import com.blogs.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
//    @Modifying
//    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :id")
//    void incrementLike(@Param("id") Integer id);

    List<Post> findAllByOrderByCreatedAtDesc();

}
