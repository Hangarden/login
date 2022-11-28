package hello.login.web.posts;

import hello.login.web.mapper.PostMapper;
import hello.login.web.model.PostRequest;
import hello.login.web.model.PostResponse;
import hello.login.web.model.SearchDto;
import hello.login.web.model.ViewDTO;
import hello.login.web.paging.Pagination;
import hello.login.web.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    /**
     * 게시글 저장
     * @param params - 게시글 정보
     * @return Generated PK
     */
    @Transactional
    public Long savePost(final PostRequest params) {
        postMapper.save(params);
        return params.getId();
    }

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    public PostResponse findPostById(final Long id) {
        return postMapper.findById(id);
    }

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     * @return PK
     */
    @Transactional
    public Long updatePost(final PostRequest params) {
        postMapper.update(params);
        return params.getId();
    }
    @Transactional
    public void updateViewCount(final PostResponse params) {
        postMapper.updateViewCount(params);
    }


    /**
     * 게시글 삭제
     * @param id - PK
     * @return PK
     */
    public Long deletePost(final Long id) {
        postMapper.deleteById(id);
        return id;
    }

    // 조회수 올리기
    public int updateCnt(Long id)  {
        return postMapper.updateCnt(id);
    }

    public int getCount(Long id)  {
        return postMapper.getCount(id);
    }

    /**
     * 게시글 리스트 조회
     * @param params - search conditions
     * @return 게시글 리스트
     */
    public PagingResponse<PostResponse> findAllPost(final SearchDto params) {
        int count = postMapper.count(params);  //데이터 개수 카운팅 데이터가없는 경우 null전달하고 종료되도록
        Pagination pagination = new Pagination(count, params); //데이터가 존재 시 실행, Pagination 객체 생성하여 처리에 필요한 데이터 계산 public Pagination 실행 되어 계산됩니다.
        params.setPagination(pagination); //계산된 정보를 params에 저장

        List<PostResponse> list = postMapper.findAll(params); // findAll 쿼리 실행하여 LIMIT 구문 실행
        return new PagingResponse<>(list, pagination); //데이터와 계산된 페이지 정보 전달 하여 객체 리턴하고 로직 종룟
    }

    public void viewUpdate(ViewDTO viewDTO) {
        postMapper.saveView(viewDTO);
    }
    //        int count = postMapper.count(params);
//        if (count < 1) {
//            return new PagingResponse<>(Collections.emptyList(), null);
//        }
//
//        Pagination pagination = new Pagination(count, params);
//        params.setPagination(pagination);
//
//        List<PostResponse> list = postMapper.findAll(params);
//        return new PagingResponse<>(list, pagination);
//    }

}