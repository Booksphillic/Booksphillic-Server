package com.booksphillic.repository;

import com.booksphillic.service.board.GetPostsRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;

    // 특정 동네 컬렉션 조회
    public List<GetPostsRes> selectSameDistrictPosts(String district, int offset, int limit) {
        String sql = "SELECT p.post_id as postId, p.title as title, p.content as content, b.district as district, e.name as editorName FROM post p " +
                "JOIN bookstore b on p.store_id = b.store_id " +
                "JOIN editor e on p.editor_id = e.editor_id " +
                "WHERE b.district = ?" +
                "ORDER BY p.create_at DESC LIMIT ?, ?";
        Object[] params = new Object[] {district, offset, limit};
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> new GetPostsRes(
                rs.getLong("postId"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("district"),
                rs.getString("editorName")),
                params
        );
    }

    // 다른 동네 컬렉션 조회
    public List<GetPostsRes> selectOtherDistrictPosts(String district, int offset, int limit) {
        String sql = "SELECT p.post_id as postId, p.title as title, p.content as content, b.district as district, e.name as editorName FROM post p " +
                "JOIN bookstore b on p.store_id = b.store_id " +
                "JOIN editor e on p.editor_id = e.editor_id " +
                "WHERE b.district <> ?" +
                "ORDER BY p.create_at DESC LIMIT ?, ?";
        Object[] params = new Object[] {district, offset, limit};
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> new GetPostsRes(
                        rs.getLong("postId"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("district"),
                        rs.getString("editorName")),
                params
        );
    }

}