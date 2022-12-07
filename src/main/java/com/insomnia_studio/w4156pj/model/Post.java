package com.insomnia_studio.w4156pj.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*** Define Post.*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
  private UUID postId;

  private UUID clientId;
  private Set<String> tags;
  private UUID userId;
  private String title;
  private String content;
  private Date postCreatedTime;
  private Date postUpdatedTime;

  /*** Define PostMethod .*/

  public Post(UUID postId, UUID clientId, UUID userId, String title, String content) {
    this.postId = postId;
    this.clientId = clientId;
    this.userId = userId;
    this.title = title;
    this.content = content;
  }
  /*** Define PostMethod .*/

  public Post(UUID clientId, UUID userId, String title, String content, Set<String> tags) {
    this.clientId = clientId;
    this.userId = userId;
    this.title = title;
    this.content = content;
    this.tags = tags;
  }
  /*** Define PostMethod .*/

//  public Post(UUID clientId) {
//    this.clientId = clientId;
//  }
}
