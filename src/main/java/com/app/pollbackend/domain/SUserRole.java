package com.app.pollbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "s_user_role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Audited
public class SUserRole extends Auditable<String>{
	@Id
	@SequenceGenerator(name = "s_user_role_seq", sequenceName = "s_user_role_seq", initialValue = 5, allocationSize = 1)
	@GeneratedValue(generator = "s_user_role_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	
}
