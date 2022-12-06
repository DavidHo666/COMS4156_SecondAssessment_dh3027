package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.model.Token;
import com.insomnia_studio.w4156pj.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


@WebMvcTest(PostControllerTest.class)

public class PostControllerTest {

  @Mock
  private PostService postService;


  @InjectMocks
  private PostController postController;


  @DisplayName("Test for AddPost Method")
  @Test
  public void testAddPost() throws Exception {

    UUID postId = UUID.randomUUID();
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");
    when(postService.addPost(post)).thenReturn(post);
    Post addedpost = postController.addPost(post);
    assertEquals(post, addedpost);
  }


  @DisplayName("Test for getpostbyid Method non-null return")
  @Test
  public void testGetPostByPostIdTrue() throws Exception {
    //setup
    UUID postId = UUID.randomUUID();
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");
    Token token = new Token(clientId);

    //when
    when(postService.getPostById(postId, token)).thenReturn(post);

    //test
    Post foundPost = postController.getPostByPostId(postId, token);

    //assert
    assertEquals(post, foundPost);
  }


  @DisplayName("Test for getpostbyid Method null return")
  @Test
  public void testgetPostByPostNull() throws Exception {
    //setup
    UUID postId = UUID.randomUUID();
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");
    Token token = new Token(clientId);

    //when
    when(postService.getPostById(postId, token)).thenReturn(null);

    //test
    Post foundPost = postController.getPostByPostId(postId, token);

    //assert
    assertNull(foundPost);

  }


  @DisplayName("Test for updatepostbyid Method non-null return")
  @Test
  public void testUpdatePostByPostIdTrue() throws Exception {
    //setup
    UUID postId = UUID.randomUUID();
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");

    //when
    when(postService.updatePostById(postId, post)).thenReturn(post);

    //test
    Post updatePost = postController.updatePostByPostId(postId, post);

    //assert
    assertEquals(post, updatePost);
  }


  @DisplayName("Test for updatepostbyid Method null return")
  @Test
  public void testUpdatePostByPostNull() throws Exception {
    //setup
    UUID postId = UUID.randomUUID();
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");

    //when
    when(postService.updatePostById(postId, post)).thenReturn(null);

    //test
    Post updatePost = postController.updatePostByPostId(postId, post);

    //assert
    assertNull(updatePost);
  }


  @DisplayName("Test for deletePostByPostId method")
  @Test
  public void testDeletePostByPostId() throws Exception {
    //setup
    UUID postId = UUID.randomUUID();
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");
    Token token = new Token(clientId);
    Map<String, Boolean> expectResponse = new HashMap<>();
    expectResponse.put("Deleted", Boolean.TRUE);

    //when
    when(postService.deletePostById(postId, token)).thenReturn(Boolean.TRUE);

    //test
    Map<String, Boolean> deleteResponse = postController.deletePostByPostId(postId, token);

    //assert
    assertEquals(expectResponse, deleteResponse);
  }

  @DisplayName("Test for deletePostByPostId method fail to delete")
  @Test
  public void testDeletePostByPostIdFalse() throws Exception {
    //setup
    UUID postId = UUID.randomUUID();
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");
    Token token = new Token(clientId);
    Map<String, Boolean> expectResponse = new HashMap<>();
    expectResponse.put("Deleted", Boolean.FALSE);

    //when
    when(postService.deletePostById(postId, token)).thenReturn(Boolean.FALSE);

    //test
    Map<String, Boolean> deleteResponse = postController.deletePostByPostId(postId, token);

    //assert
    assertEquals(expectResponse, deleteResponse);
  }


}
