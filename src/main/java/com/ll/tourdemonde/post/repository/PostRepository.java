package com.ll.tourdemonde.post.repository;

import com.ll.tourdemonde.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);


    @Query("select "
            + "distinct p "
            + "from Post p "
            + "left outer join Member m1 on p.author=m1 "
            + "left outer join Comment c on c.post=p "
            + "left outer join Member m2 on c.author=m2 "
            + "where "
            + "   p.title like %:kw% "
            + "   or m1.username like %:kw% "
            + "   or c.content like %:kw% "
            + "   or m2.username like %:kw% ")
    Page<Post> findAllByKeyword(@Param("kw") String kw, Pageable pageable);


    Page<Post> findAllByCategory(@Param("category") String category, Pageable pageable);
}
