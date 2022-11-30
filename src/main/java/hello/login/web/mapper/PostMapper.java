package hello.login.web.mapper;


import hello.login.web.model.SearchDto;
import hello.login.web.model.PostRequest;
import hello.login.web.model.PostResponse;
import hello.login.web.model.ViewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    /**
     * 게시글 저장
     * @param params - 게시글 정보
     */
    void save(PostRequest params);

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    PostResponse findById(Long id);

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     */
    void update(PostRequest params);

    /**
     * 게시글 삭제
     * @param id - PK
     */
    void deleteById(Long id);

    /**
     * 게시글 리스트 조회
     * @param params - search conditions
     * @return 게시글 리스트
     */
    List<PostResponse> findAll(SearchDto params);

    /**
     * 게시글 수 카운팅
     * @param params - search conditions
     * @return 게시글 수
     */
    int count(SearchDto params);

    int updateCnt(Long id);

    void saveView(ViewDTO viewDTO);

    Integer getCount(Long id);

    void updateViewCount(PostResponse params);

//    void saveViewId(int viewCount);
}