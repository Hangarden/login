package hello.login.web.comment;


import hello.login.web.mapper.CommentMapper;
import hello.login.web.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    public boolean registerComment(CommentDTO params) {
        int queryResult = 0;

        if (params.getIdx() == null) {
            queryResult = commentMapper.insertComment(params);
        } else {
            queryResult = commentMapper.updateComment(params);
        }

        return (queryResult == 1) ? true : false;
    }

    public boolean registerReComment(CommentDTO params) {
        int queryResult = 0;

        if (params.getIdx() == null) {
            queryResult = commentMapper.insertReComment(params);
        } else {
//            queryResult = commentMapper.updateReComment(params);
        }

        return (queryResult == 1) ? true : false;
    }

    public boolean deleteComment(Long idx) {
        int queryResult = 0;

        CommentDTO comment = commentMapper.selectCommentDetail(idx);

        if (comment != null && "N".equals(comment.getDeleteYn())) {
            queryResult = commentMapper.deleteComment(idx);
        }

        return (queryResult == 1) ? true : false;
    }

    public CommentDTO selectComment(Long idx) {
        int queryResult = 0;
        CommentDTO comment = commentMapper.selectCommentDetail(idx);

        return comment;
    }

    public boolean deleteCheck(Long idx) {
        int queryResult = 0;
        CommentDTO comment = commentMapper.selectCommentDetail(idx);

        if (comment != null && "N".equals(comment.getDeleteYn())) {
            queryResult = commentMapper.deleteComment(idx);
        }

        return (queryResult == 1) ? true : false;
    }

    public List<CommentDTO> getCommentList(CommentDTO params) {
        List<CommentDTO> commentList = Collections.emptyList();

        int commentTotalCount = commentMapper.selectCommentTotalCount(params);
        if (commentTotalCount > 0) {
            commentList = commentMapper.selectCommentList(params);
        }

        return commentList;
    }

    public List<CommentDTO> getReCommentList(CommentDTO params) {
        List<CommentDTO> commentList1 = Collections.emptyList();

        int commentTotalCount = commentMapper.selectReCommentTotalCount(params);
        if (commentTotalCount > 0) {
            commentList1 = commentMapper.selectReCommentList(params);
        }

        return commentList1;
    }

}