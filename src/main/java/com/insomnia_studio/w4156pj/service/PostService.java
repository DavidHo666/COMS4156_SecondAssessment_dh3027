package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.model.Token;

import java.util.UUID;

/*** Define PostService.*/
public interface PostService {
  Post addPost(Post post) throws Exception;

  Post getPostById(UUID postId, Token post) throws Exception;

  Post updatePostById(UUID postId, Post post) throws Exception;

  Boolean deletePostById(UUID postId, Token token) throws Exception;

}