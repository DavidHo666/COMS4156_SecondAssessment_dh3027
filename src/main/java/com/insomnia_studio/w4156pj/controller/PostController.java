package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.model.Token;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import com.insomnia_studio.w4156pj.service.PostService;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*** Define PostController.*/
@RestController
@RequestMapping("/api/v1/post")
@AllArgsConstructor
public class PostController {
  private PostService postService;
  private PostEntityRepository postEntityRepository;

  @PostMapping
  public Post addPost(@RequestBody Post post) throws Exception {
    post = postService.addPost(post);
    return post;
  }

  @GetMapping("/{postId}")
  public Post getPostByPostId(@PathVariable UUID postId, @RequestBody Token token) throws Exception {
    return postService.getPostById(postId, token);
  }

  @PutMapping("/{postId}")
  public Post updatePostByPostId(@PathVariable UUID postId,
                                 @RequestBody Post post) throws Exception {
    return postService.updatePostById(postId, post);
  }

  /*** Define PostMethod .*/
  @DeleteMapping("/{postId}")
  @Transactional
  public Map<String, Boolean> deletePostByPostId(@PathVariable UUID postId, @RequestBody Token token)
          throws Exception {
    Map<String, Boolean> response = new HashMap<>();
    boolean is_deleted = (postService.deletePostById(postId, token));
    response.put("Deleted", is_deleted);
    return response;
  }
}