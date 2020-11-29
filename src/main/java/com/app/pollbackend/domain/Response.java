package com.app.pollbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "response")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Audited
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
        ,
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Response extends Auditable<String>{
	@Id
    @GeneratedValue(generator = "response_seq_generator")
    @SequenceGenerator( name = "response_seq_generator", sequenceName = "response_seq", initialValue = 5, allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private SUser user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    //@JsonBackReference(value="question_responses")
    private Question question;

    @Type(type = "jsonb")
    @Column(name = "response", columnDefinition = "jsonb")
    private String response;
}
