package next.model;

import java.time.LocalDateTime;

/*
    answerId 			bigint				auto_increment,
	writer				varchar(30)			NOT NULL,
	contents			varchar(5000)		NOT NULL,
	createdDate			timestamp			NOT NULL,
	questionId			bigint				NOT NULL,
 */
public class Answer {

    private Long answerId;

    private String writer;

    private String contents;

    private LocalDateTime createdDate;

    private Long questionId;

    public Answer(Long answerId, String writer, String contents, LocalDateTime createdDate, Long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public Answer(String writer, String contents, LocalDateTime createdDate, Long questionId) {
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public void update(String contents) {
        this.contents = contents;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", questionId=" + questionId +
                ", createdDate=" + createdDate +
                '}';
    }
}

