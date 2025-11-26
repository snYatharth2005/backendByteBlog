package com.blogs.Repository;

import com.blogs.Model.Post;
import com.blogs.Model.PostLiked;
import com.blogs.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLiked, Integer> {

    boolean existsByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);

    int countByPost(Post post);

}
