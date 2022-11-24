package hello.login.web.paging;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PagingResponse<T> {

    private List<T> list = new ArrayList<>(); //T는 객체 생성시 해당 타입으로 변경, PostResponse 를 받음
    private Pagination pagination; //계산된 페이지 정보를 담아 화면(HTML)으로 전달하는 용도로 사용됩니다.

    public PagingResponse(List<T> list, Pagination pagination) {
        this.list = list; //, PostResponse 를 받음
        this.pagination = pagination;
    }

}
