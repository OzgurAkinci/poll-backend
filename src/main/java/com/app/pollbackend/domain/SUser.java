package com.app.pollbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "s_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Audited
public class SUser extends Auditable<String>{
	@Id
	@SequenceGenerator(name = "s_user_seq", sequenceName = "s_user_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "s_user_seq", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String username;
	private String password;
	private String name;
	private String surname;
	private String address;
	private String email;
	private String phone;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name="s_user_role",
        joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
        inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
    private List<SRole> roles;

	@OneToMany(mappedBy = "question")
	@JsonManagedReference
	@JsonIgnoreProperties({"user","question"})
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Response> responses = new ArrayList<>();
}
