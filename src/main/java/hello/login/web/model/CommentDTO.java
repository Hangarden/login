package hello.login.web.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends CommonDTO{

    private Integer idx;

    private Integer boardIdx;

    private String content;

    private String writer;

    private Integer memberKey;

    private Integer cDepth; //대댓글 추가

    private Integer commentParentId; //대댓글 추가
}
