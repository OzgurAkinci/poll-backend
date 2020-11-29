package com.app.pollbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "option")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class Option extends Auditable<String>{
	@Id
    @GeneratedValue(generator = "option_seq_generator")
    @SequenceGenerator( name = "option_seq_generator", sequenceName = "option_seq", initialValue = 5, allocationSize = 1)
    private Integer id;
	
	@Column(name="option_title")
	private String optionTitle;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnoreProperties({"options"})
    private Question question;
}
