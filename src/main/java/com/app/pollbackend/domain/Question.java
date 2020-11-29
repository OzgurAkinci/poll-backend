package com.app.pollbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "question")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class Question extends Auditable<String>{
	@Id
    @GeneratedValue(generator = "question_seq_generator")
    @SequenceGenerator( name = "question_seq_generator", sequenceName = "question_seq", initialValue = 5, allocationSize = 1)
    private Integer id;
	
	@Column(name="question_title")
	private String questionTitle;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    //@JsonBackReference(value="poll_questions")
    @JsonIgnoreProperties({"questions"})
    private Poll poll;

    @Column(name = "question_type")
    private Integer questionType;

    @OneToMany(mappedBy = "question", orphanRemoval = true)
    //@JsonManagedReference(value="question_options")
    @JsonIgnoreProperties({"question"})
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Option> options = new ArrayList<>();

    @OneToMany(mappedBy = "question", orphanRemoval = true)
    //@JsonManagedReference(value="question_responses")
    @JsonIgnoreProperties({"question"})
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Response> responses = new ArrayList<>();
}
