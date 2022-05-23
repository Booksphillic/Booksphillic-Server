package com.booksphillic.repository;

import com.booksphillic.domain.board.PostCategory;
import com.booksphillic.service.board.dto.GetPostsRes;
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
        String sql = "SELECT p.post_id as postId, p.category as category, p.title as title, p.content1 as content, " +
                "b.district as district, b.profile_img as storeImgUrl, e.name as editorName FROM post p " +
                "JOIN bookstore b on p.store_id = b.store_id " +
                "JOIN editor e on p.editor_id = e.editor_id " +
                "WHERE b.district = ?" +
                "ORDER BY p.created_at DESC LIMIT ?, ?";

        return selectDistrictPosts(district, offset, limit, sql);
    }

    // 다른 동네 컬렉션 조회
    public List<GetPostsRes> selectOtherDistrictPosts(String district, int offset, int limit) {
        String sql = "SELECT p.post_id as postId, p.category as category, p.title as title, p.content1 as content, " +
                "b.district as district, b.profile_img as storeImgUrl, e.name as editorName FROM post p " +
                "JOIN bookstore b on p.store_id = b.store_id " +
                "JOIN editor e on p.editor_id = e.editor_id " +
                "WHERE b.district <> ?" +
                "ORDER BY p.created_at DESC LIMIT ?, ?";

        return selectDistrictPosts(district, offset, limit, sql);
    }

    // 전체 동네 조회
    public List<GetPostsRes> selectAllDistrictPosts(int offset, int limit) {
        String sql = "SELECT p.post_id as postId, p.category as category, p.title as title, p.content1 as content, " +
                "b.district as district, b.profile_img as storeImgUrl, e.name as editorName FROM post p " +
                "JOIN bookstore b on p.store_id = b.store_id " +
                "JOIN editor e on p.editor_id = e.editor_id " +
                "ORDER BY p.created_at DESC LIMIT ?, ?";

        Object[] params = new Object[]{offset, limit};

        return getGetPostsRes(sql, params);
    }


    private List<GetPostsRes> getGetPostsRes(String sql, Object[] params) {
        return this.jdbcTemplate.query(sql, (rs, rowNum) ->
            GetPostsRes.builder()
                    .postId(rs.getLong("postId"))
                    .category(PostCategory.valueOf(rs.getString("category")))
                    .title(rs.getString("title"))
                    .content(rs.getString("content"))
                    .district(rs.getString("district"))
                    .storeImgUrl(rs.getString("storeImgUrl"))
                    .editorName(rs.getString("editorName"))
                    .build(), params);
    }

    private List<GetPostsRes> selectDistrictPosts(String district, int offset, int limit, String sql) {
        Object[] params = new Object[]{district, offset, limit};
        return getGetPostsRes(sql, params);
    }

}