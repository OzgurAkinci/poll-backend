package com.app.pollbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "poll")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class Poll extends Auditable<String> implements Serializable {
	@Id
    @GeneratedValue(generator = "poll_seq_generator")
    @SequenceGenerator( name = "poll_seq_generator", sequenceName = "poll_seq", initialValue = 5, allocationSize = 1)
    private Integer id;
	
	@Column(name="poll_title")
	private String pollTitle;

    @OneToMany(mappedBy = "poll", fetch = FetchType.EAGER)
    @JsonManagedReference(value="poll_questions")
    @JsonIgnoreProperties({"options","responses"})
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Question> questions = new ArrayList<>();
}
