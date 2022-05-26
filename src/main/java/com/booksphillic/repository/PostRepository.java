package com.booksphillic.repository;

import com.booksphillic.domain.board.PostCategory;
import com.booksphillic.service.board.dto.GetPostsRes;
import com.booksphillic.service.tag.dto.GetStorePostsRes;
import lombok.RequiredArgsConstructor;
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
                "LEFT JOIN bookstore b on p.store_id = b.store_id " +
                "LEFT JOIN editor e on p.editor_id = e.editor_id " +
                "WHERE b.district = ?" +
                "ORDER BY p.created_at ASC LIMIT ?, ?";

        return selectDistrictPosts(district, offset, limit, sql);
    }

    // 다른 동네 컬렉션 조회
    public List<GetPostsRes> selectOtherDistrictPosts(String district, int offset, int limit) {
        String sql = "SELECT p.post_id as postId, p.category as category, p.title as title, p.content1 as content, " +
                "b.district as district, b.profile_img as storeImgUrl, e.name as editorName FROM post p " +
                "LEFT JOIN bookstore b on p.store_id = b.store_id " +
                "LEFT JOIN editor e on p.editor_id = e.editor_id " +
                "WHERE b.district <> ?" +
                "ORDER BY p.created_at ASC LIMIT ?, ?";

        return selectDistrictPosts(district, offset, limit, sql);
    }

    // 전체 동네 조회
    public List<GetPostsRes> selectAllDistrictPosts(int offset, int limit) {
        String sql = "SELECT p.post_id as postId, p.category as category, p.title as title, p.content1 as content, " +
                "b.district as district, b.profile_img as storeImgUrl, e.name as editorName FROM post p " +
                "LEFT JOIN bookstore b on p.store_id = b.store_id " +
                "LEFT JOIN editor e on p.editor_id = e.editor_id " +
                "ORDER BY p.created_at ASC LIMIT ?, ?";

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


    // 책방의 컬렉션 조회
    public List<GetStorePostsRes> selectStorePosts(Long storeId) {
        String sql = "SELECT p.post_id as postId, p.title as title, p.content1 as content, p.category as category, " +
                "b.district as district, b.profile_img as storeImgUrl FROM post p " +
                "LEFT JOIN bookstore b on p.store_id = b.store_id " +
                "WHERE p.store_id = ? " +
                "ORDER BY p.created_at ASC";

        Object[] params = new Object[]{storeId};

        return this.jdbcTemplate.query(sql, (rs, rowNum) ->
                GetStorePostsRes.builder()
                        .postId(rs.getLong("postId"))
                        .category(PostCategory.valueOf(rs.getString("category")))
                        .title(rs.getString("title"))
                        .content(rs.getString("content"))
                        .district(rs.getString("district"))
                        .profileImg(rs.getString("storeImgUrl"))
                        .build(), params);
    }



}