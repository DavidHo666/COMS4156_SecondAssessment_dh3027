package com.insomnia_studio.w4156pj.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class CommentEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID commentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
//    @JsonManagedReference
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="clientId")
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonManagedReference
    @JoinColumn(name="postId")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.EAGER)
    private CommentEntity parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CommentEntity> childComments = new HashSet<>();

    private Integer LikesNum;

    private Integer dislikesNum;

    @Lob
    private String Content;
}
