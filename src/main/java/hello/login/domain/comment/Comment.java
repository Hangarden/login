//package hello.login.domain.comment;
//
//import hello.login.domain.Posts.Posts;
//import lombok.*;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import javax.persistence.*;
//
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Table(name = "comments")
//@Entity
//public class Comment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(columnDefinition = "TEXT", nullable = false)
//    private String comment; // 댓글 내용
//
////    @Column(name = "created_date")
////    @CreatedDate
////    private String createdDate;
////
////    @Column(name = "modified_date")
////    @LastModifiedDate
////    private String modifiedDate;
//
//    @ManyToOne
//    @JoinColumn(name = "posts_id")
//    private Posts posts;
//
//    private String author;
//
//
////    @ManyToOne
////    @JoinColumn(name = "user_id")
////    private Member user; // 작성자
//}
