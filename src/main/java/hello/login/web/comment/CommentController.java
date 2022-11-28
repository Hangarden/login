package hello.login.web.comment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hello.login.web.adapter.GsonLocalDateTimeAdapter;
import hello.login.web.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @RequestMapping(value = { "/comments" }, method = { RequestMethod.POST }) //수정시에는 comment/{idx}로 전송
    public JsonObject registerComment(@PathVariable(value = "idx", required = false) Integer idx, @RequestBody final CommentDTO params, HttpSession session) {

        JsonObject jsonObj = new JsonObject();

        try {
//            if (idx != null) { //idx가 null이 아니라는 것은 댓글번호, PK가 포함되어 있다는 의미, PK가 포함되어 있다는 것은 이미 생성된 댓글임을 의미한다
//                params.setIdx(idx);
//            }
            Object ob1 = session.getAttribute("NAME");
            String mySessionName = (String)ob1;
            params.setWriter(mySessionName);
            Object ob2 = session.getAttribute("MEMBER_ID");
            Integer mySessionId = (Integer)ob2;
            params.setMemberKey(mySessionId);
            int depth = 0; //일반 댓글은 depth가 0
            int parentId = 0;
            params.setCDepth(depth); // 뎁스 주입
            params.setCommentParentId(parentId); // 부모 댓글 번호 주입, 대댓글이 아닌 댓글은 0으로
            boolean isRegistered = commentService.registerComment(params); //boolean 타입 변수인 isRegistered 에는 CommentService의 registerComment의 메서드의 실행 결과를 저장한다. 실행 된다면 true 아니라면 false를 저장
            jsonObj.addProperty("result", isRegistered); //결과를 result라는 이름의 프로퍼티에 JSON 객체에 추가해서 리턴합니다.

        } catch (DataAccessException e) {
            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");  //삭제되었거나, 없는 게시글 번호(boardIdx)를 지정한 상태

        } catch (Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }

        return jsonObj;
    }

    @PostMapping(value = "/reComments" ) //수정시에는 comment/{idx}로 전송
    public CommentDTO registerReComment(@PathVariable(value = "idx", required = false) Integer idx, @RequestBody final CommentDTO params, HttpSession session) {


        try {
            if (idx != null) { //idx가 null이 아니라는 것은 댓글번호, PK가 포함되어 있다는 의미, PK가 포함되어 있다는 것은 이미 생성된 댓글임을 의미한다
                params.setIdx(idx);
            }

            Object ob1 = session.getAttribute("NAME");
            String mySessionName = (String)ob1;
            params.setWriter(mySessionName);
            Object ob2 = session.getAttribute("MEMBER_ID");
            Integer mySessionId = (Integer)ob2;
            params.setMemberKey(mySessionId);
            int depth = 1; //일반 댓글은 depth가 0
            params.setCDepth(depth); // 뎁스 주입
            commentService.registerReComment(params); //boolean 타입 변수인 isRegistered 에는 CommentService의 registerComment의 메서드의 실행 결과를 저장한다. 실행 된다면 true 아니라면 false를 저장
//            jsonObj.addProperty("result1", isRegistered); //결과를 result라는 이름의 프로퍼티에 JSON 객체에 추가해서 리턴합니다.

        } catch (DataAccessException e) {
//            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");  //삭제되었거나, 없는 게시글 번호(boardIdx)를 지정한 상태

        } catch (Exception e) {
//            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }

        return params;
    }

    @RequestMapping(value = { "/comments/{idx}" }, method = { RequestMethod.PATCH }) //수정시에는 comment/{idx}로 전송
    public JsonObject updateComment(@PathVariable(value = "idx", required = false) Integer idx, @RequestBody final CommentDTO params, HttpSession session) {

        JsonObject jsonObj = new JsonObject();

        try {
            if (idx != null) { //idx가 null이 아니라는 것은 댓글번호, PK가 포함되어 있다는 의미, PK가 포함되어 있다는 것은 이미 생성된 댓글임을 의미한다
                params.setIdx(idx);
            }
            int depth = 0; //일반 댓글은 depth가 0
            int parentId = 0;
            params.setCDepth(depth); // 뎁스 주입
            params.setCommentParentId(parentId); // 부모 댓글 번호 주입, 대댓글이 아닌 댓글은 0으로
            boolean isRegistered = commentService.registerComment(params); //boolean 타입 변수인 isRegistered 에는 CommentService의 registerComment의 메서드의 실행 결과를 저장한다. 실행 된다면 true 아니라면 false를 저장
            jsonObj.addProperty("result", isRegistered); //결과를 result라는 이름의 프로퍼티에 JSON 객체에 추가해서 리턴합니다.

        } catch (DataAccessException e) {
            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");  //삭제되었거나, 없는 게시글 번호(boardIdx)를 지정한 상태

        } catch (Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }

        return jsonObj;
    }


    @GetMapping(value = "/comments/{boardIdx}")
    public JsonObject getCommentList(@PathVariable("boardIdx") Long boardIdx, @ModelAttribute("params") CommentDTO params, Model model, HttpSession session) {

        JsonObject jsonObj = new JsonObject(); //json객체 생성

        List<CommentDTO> commentList = commentService.getCommentList(params); //결과를 List에 저장
        if (CollectionUtils.isEmpty(commentList) == false) { //댓글이 1개 이상이면
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
            JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray(); //gson  직렬화(자바 객체 → JSON), 역직렬화(JSON → 자바 객체) 처리
            jsonObj.add("commentList", jsonArr); //json객체에 commentList라는 프로퍼티를 추가해서 리턴
            Object ob2 = session.getAttribute("MEMBER_ID");
            Integer mySessionId = (Integer)ob2;
            model.addAttribute("memberCheck", mySessionId);
        }

        return jsonObj;
    }
//    @PostMapping(value = {"/comments/{boardIdx}/{commentIdx}"})
//    public JsonObject getReCommentList(@PathVariable("boardIdx") Long boardIdx, @PathVariable("commentIdx") Integer commentParentId, @ModelAttribute("params") CommentDTO params, Model model, HttpSession session) {
//
//        JsonObject jsonObj = new JsonObject(); //json객체 생성
//        params.setCommentParentId(commentParentId);
//        List<CommentDTO> commentList1 = commentService.getReCommentList(params); //boardIdx와 commentParentId가 저장된 param을 서비스에 전달
//        if (CollectionUtils.isEmpty(commentList1) == false) { //댓글이 1개 이상이면
//            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
//            JsonArray jsonArr = gson.toJsonTree(commentList1).getAsJsonArray(); //gson  직렬화(자바 객체 → JSON), 역직렬화(JSON → 자바 객체) 처리
//            jsonObj.add("commentList1", jsonArr); //json객체에 commentList라는 프로퍼티를 추가해서 리턴
//            Object ob2 = session.getAttribute("MEMBER_ID");
//            Integer mySessionId = (Integer)ob2;
//            model.addAttribute("memberCheck", mySessionId);
//        }
//
//        return jsonObj;
//    }

    @GetMapping(value = {"/comments/{boardIdx}/{commentIdx}"})
    public JsonObject getReCommentList(@PathVariable("boardIdx") Long boardIdx, @PathVariable("commentIdx") Integer commentParentId, @ModelAttribute("params") CommentDTO params, Model model, HttpSession session) {

        JsonObject jsonObj = new JsonObject(); //json객체 생성
        params.setCommentParentId(commentParentId);
        List<CommentDTO> commentList1 = commentService.getReCommentList(params); //boardIdx와 commentParentId가 저장된 param을 서비스에 전달
        if (CollectionUtils.isEmpty(commentList1) == false) { //댓글이 1개 이상이면
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
            JsonArray jsonArr = gson.toJsonTree(commentList1).getAsJsonArray(); //gson  직렬화(자바 객체 → JSON), 역직렬화(JSON → 자바 객체) 처리
            jsonObj.add("commentList1", jsonArr); //json객체에 commentList라는 프로퍼티를 추가해서 리턴
            Object ob2 = session.getAttribute("MEMBER_ID");
            Integer mySessionId = (Integer)ob2;
            model.addAttribute("memberCheck", mySessionId);
        }

        return jsonObj;
    }


//    @DeleteMapping(value = "/comments/{idx}")
//    public JsonObject deleteComment(@PathVariable("idx") final Long idx) {
//
//        JsonObject jsonObj = new JsonObject();
//
//        try {
//            boolean isDeleted = commentService.deleteComment(idx);
//            jsonObj.addProperty("result", isDeleted);
//
//        } catch (DataAccessException e) {
//            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
//
//        } catch (Exception e) {
//            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
//        }
//
//        return jsonObj;
//    }

    @DeleteMapping(value = "/comments/{idx}")
    public JsonObject deleteComment(@PathVariable("idx") final Long idx) {

        JsonObject jsonObj = new JsonObject();

        CommentDTO deleteComment = commentService.selectComment(idx);
        int depth = deleteComment.getCDepth();

        if (depth == 0) {
            if (commentService.deleteCheck(idx) == false) {
                boolean isDeleted = commentService.deleteComment(idx);
                jsonObj.addProperty("result", isDeleted);
            }
        } else if (depth == 1) {
            boolean isDeleted = commentService.deleteComment(idx);
            jsonObj.addProperty("result", isDeleted);
        }

        return jsonObj;
    }

}