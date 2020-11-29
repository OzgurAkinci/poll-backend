package com.app.pollbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "s_permission")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SPermission extends Auditable<String> implements Serializable {

    @Id
    @GenericGenerator(name = "gen_s_permission_seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "hibernate_sequence", value = "s_permission_seq"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "s_permission_seq")}
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_s_permission_seq")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "perm_level")
    private Integer permLevel;

    @Column(name = "parent_id", insertable = false, updatable = false)
    private Integer parentId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "parent_id", nullable = true, updatable = false)
    public SPermission parentPermission;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentPermission", orphanRemoval = true)
    @OrderBy("name")
    public List<SPermission> subPermissions;


}
