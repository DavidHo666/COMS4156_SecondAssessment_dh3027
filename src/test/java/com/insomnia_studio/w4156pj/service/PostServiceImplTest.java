package com.insomnia_studio.w4156pj.service;


import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.CommentEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.model.Token;
import com.insomnia_studio.w4156pj.repository.ClientEntityRepository;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import com.insomnia_studio.w4156pj.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {
  @Mock
  private PostEntityRepository postRepository;
  @Mock
  private ClientEntityRepository clientEntityRepository;
  @Mock
  private UserEntityRepository userRepository;

  @InjectMocks
  private PostServiceImpl postserviceimpl;


  private ClientEntity client;
  private UserEntity user;
  private Post post;

  private Post postclient;
  private PostEntity postEntity;
  private Post expectedpost;

  private Post newpost;
  private PostEntity newPostEntity;
  private Post updatePost;

  private Token token;

  @BeforeEach
  public void setup() {
    //setup client
    client = new ClientEntity();
    client.setClientId(UUID.randomUUID());
    //setup user
    user = new UserEntity();
    user.setUserId(UUID.randomUUID());
    user.setClient(client);
    //setup post
    post = new Post();
    post.setClientId(client.getClientId());
    post.setUserId(user.getUserId());
    post.setTitle("title");
    post.setContent("content");
    post.setTags(new HashSet<>(List.of("tag")));
    post.setUserId(user.getUserId());
    token = new Token();
    token.setClientId(client.getClientId());

    //setup postEntity
    postEntity = new PostEntity();
    BeanUtils.copyProperties(post, postEntity);
    postEntity.setUser(user);
    postEntity.setClient(client);
    postEntity.setPostId(UUID.randomUUID());
    postEntity.setPostCreatedTime(new Date());
    postEntity.setPostUpdatedTime(new Date());
    //setup expected post to add
    expectedpost = new Post();
    BeanUtils.copyProperties(postEntity, expectedpost);
    expectedpost.setUserId(postEntity.getUser().getUserId());
    expectedpost.setClientId(postEntity.getClient().getClientId());

    //set update
    newpost = new Post();
    newpost.setPostId(postEntity.getPostId());
    newpost.setTags(new HashSet<>(List.of("newtag")));
    newpost.setTitle("newtitle");
    newpost.setContent("newcontent");
    newpost.setClientId(postEntity.getClient().getClientId());
    newPostEntity = new PostEntity();
    BeanUtils.copyProperties(postEntity, newPostEntity);
    newPostEntity.setTags(newpost.getTags());
    newPostEntity.setTitle(newpost.getTitle());
    newPostEntity.setContent(newpost.getContent());
    updatePost = new Post();
    BeanUtils.copyProperties(newPostEntity, updatePost);
    updatePost.setUserId(newPostEntity.getUser().getUserId());
    updatePost.setClientId(newPostEntity.getClient().getClientId());


  }


  //JUnit Test for testAddPostsuccess
  @Test
  public void testAddPostsuccess() {
    // when
    when(clientEntityRepository.findByClientId(post.getClientId())).thenReturn(client);
    when(userRepository.findByUserId(post.getUserId())).thenReturn(user);
    when(clientEntityRepository.existsByClientId(post.getClientId())).thenReturn(true);
    when(postRepository.save(Mockito.any(PostEntity.class))).thenReturn(postEntity);
    //test
    Post addedpost = postserviceimpl.addPost(post);
    //assertion
    assertEquals(expectedpost, addedpost);
  }

  //JUnit Test for testAddPostClientNotExist
  @Test
  public void testAddPostClientNotExist() {
    // when
    when(clientEntityRepository.existsByClientId(post.getClientId())).thenReturn(false);

    ResponseStatusException e = assertThrows(ResponseStatusException.class, () ->
        postserviceimpl.addPost(post));
    assertEquals(HttpStatus.FORBIDDEN, e.getStatus());
    //assertEquals("403 FORBIDDEN \"Invalid Client ID\" ",e.getMessage());
  }

  //JUnit Test for testAddPostUserNotFound
  @Test
  public void testAddPostUserNotFound() {
    //when(clientEntityRepository.findByClientId(post.getClientId())).thenReturn(client);
    when(userRepository.findByUserId(post.getUserId())).thenReturn(null);
    when(clientEntityRepository.existsByClientId(post.getClientId())).thenReturn(true);
    //when(postRepository.save(Mockito.any(PostEntity.class))).thenReturn(postEntity);
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () ->
        postserviceimpl.addPost(post));
    assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
  }

  //JUnit Test for addPostInvalidUser
  @Test
  public void testAddPostClientNotMatch() {
        /*
        //PostEntity post = new PostEntity();
        //post.setTitle("a");
        //post.setPostId(UUID.randomUUID());
        //UserEntity user = new UserEntity();
        //user.setUserId("123");
        UUID postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        UUID fakeClientId = UUID.randomUUID();
        ClientEntity client = new ClientEntity(clientId, "testClient");
        ClientEntity fakeClient = new ClientEntity(fakeClientId, "fakeClient");
        UserEntity user = new UserEntity(userId, "testUser", "testUser", client);
        PostEntity post = new PostEntity(postId, user, client);
        Comment comment = new Comment(clientId, userId, postId, 1, 0, "testComment");
        comment.setCommentId(commentId);
        CommentEntity commentEntity = new CommentEntity(commentId, user, fakeClient, post);
        commentEntity.setLikesNum(1);
        commentEntity.setDislikesNum(0);
        commentEntity.setContent("testComment");

        Mockito.when(commentEntityRepository.findByCommentId(commentId)).thenReturn(commentEntity);

        try {
            Comment foundComment = commentService.getCommentById(commentId, comment);
        } catch (ResponseStatusException e) {
            Assertions.assertEquals(e.getStatus(), HttpStatus.FORBIDDEN);
        }

        */


    UUID fakeuserId = UUID.randomUUID();
    UUID fakeClientId = UUID.randomUUID();
    ClientEntity fakeClient = new ClientEntity(fakeClientId, "fakeClient");
    UserEntity fakeuser = new UserEntity(fakeuserId, "testUser", "testUser", fakeClient);
    //postEntity.setUser(fakeuser);

    //when(clientEntityRepository.findByClientId(post.getClientId())).thenReturn(client);
    //when(userRepository.findByUserId(post.getUserId())).thenReturn(fakeuser);
    //when(clientEntityRepository.existsByClientId(post.getClientId())).thenReturn(true);
    //when(postRepository.save(Mockito.any(PostEntity.class))).thenReturn(postEntity);

    //test
    post.setUserId(fakeuserId);
    try {
      Post addpost1 = postserviceimpl.addPost(post);
    } catch (ResponseStatusException e) {
      assertEquals(e.getStatus(), HttpStatus.FORBIDDEN);
    }
    //ResponseStatusException e = assertThrows(ResponseStatusException.class, () ->
    //postserviceimpl.addPost(post));
    //assertEquals(HttpStatus.FORBIDDEN,e.getStatus());
  }


  //JUnit Test for testgetPostByIdsuccess
  @Test
  public void testgetPostByIdsuccess() {
    // when
    when(postRepository.findByPostId(post.getPostId())).thenReturn(postEntity);
    //test
    Post foundpost = postserviceimpl.getPostById(post.getPostId(), token);
    //assertion
    assertEquals(expectedpost, foundpost);
  }


  //JUnit Test for testgetPostByIdNotFound
  @Test
  public void testgetPostByIdNotFound() {
    //NullPointerException e = new NullPointerException();
    // when
    when(postRepository.findByPostId(post.getPostId())).thenReturn(null);

    //assertion
    ResponseStatusException e = assertThrows(ResponseStatusException.class, () ->
        postserviceimpl.getPostById(post.getPostId(), token));

    assertEquals(HttpStatus.NOT_FOUND, e.getStatus());

  }

  //JUnit Test for testgetPostByIdInvalidClient()
  @Test
  public void testgetPostClientNotMatch() {
    token = new Token();
    token.setClientId(client.getClientId());
    UUID fakeClientId = UUID.randomUUID();
    ClientEntity fakeClient = new ClientEntity(fakeClientId, "fakeClient");
    PostEntity fakepostEntity = new PostEntity(post.getPostId(), user, fakeClient);
    when(postRepository.findByPostId(post.getPostId())).thenReturn(fakepostEntity);

    //assertion
    //ResponseStatusException e = assertThrows(ResponseStatusException.class, () ->
    //postserviceimpl.getPostById(post.getPostId(),postclient));
    try {
      Post getpost1 = postserviceimpl.getPostById(post.getPostId(), token);
    } catch (ResponseStatusException e) {
      assertEquals(HttpStatus.FORBIDDEN, e.getStatus());
    }

  }

  //JUnit Test for updatePostByIdsuccess
  @Test
  public void testUpdatePostByIdsuccess() throws Exception {

    // when
    when(postRepository.findByPostId(Mockito.any(UUID.class))).thenReturn(postEntity);
    when(postRepository.save(Mockito.any(PostEntity.class))).thenReturn(newPostEntity);
    //test
    Post updatedpost = postserviceimpl.updatePostById(postEntity.getPostId(), newpost);

    //assertion
    assertEquals(updatePost, updatedpost);

  }

  //JUnit Test for updatePostByIdFailed
  @Test
  public void testUpdatePostByIdFailed() throws Exception {

    // when
    when(postRepository.findByPostId(postEntity.getPostId())).thenReturn(null);

    //assertion
    Exception exception = assertThrows(Exception.class, () ->
        postserviceimpl.updatePostById(postEntity.getPostId(), newpost));

    assertEquals(HttpStatus.NOT_FOUND + " \"Post ID not found\"", exception.getMessage());

  }

  //JUnit Test for deletePostById
  @Test
  public void testDeletePostById() {
    //setup
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID postId = UUID.randomUUID();

    ClientEntity client = new ClientEntity(clientId, "testClient");
    UserEntity user = new UserEntity(userId, "testUser", "testUser", client);
    PostEntity postEntity = new PostEntity(postId, user, client);
    postEntity.setTitle("testPost");
    postEntity.setContent("testPost");
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");
    Token token = new Token(clientId);
    Boolean expectResponse = true;

    //when
    Mockito.when(postRepository.findByPostId(postId)).thenReturn(postEntity);
    Mockito.when(postRepository.deletePostEntityByPostId(postId)).thenReturn(1);

    //test
    Boolean deleteResponse = postserviceimpl.deletePostById(postId, token);

    //assert
    Assertions.assertEquals(expectResponse, deleteResponse);
  }

  @Test
  public void testDeletePostByIdInvalidClient() {
    //setup
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID postId = UUID.randomUUID();
    UUID fakeClientId = UUID.randomUUID();

    ClientEntity client = new ClientEntity(clientId, "testClient");
    UserEntity user = new UserEntity(userId, "testUser", "testUser", client);
    PostEntity postEntity = new PostEntity(postId, user, client);
    postEntity.setTitle("testPost");
    postEntity.setContent("testPost");
    Post post = new Post(postId, fakeClientId, userId, "testPost", "testPost");
    Token token = new Token(clientId);
    Boolean expectResponse = true;

    //when
    Mockito.when(postRepository.findByPostId(postId)).thenReturn(postEntity);

    //test
    try {
      Boolean deleteResponse = postserviceimpl.deletePostById(postId, token);
    } catch (ResponseStatusException e) {
      Assertions.assertEquals(HttpStatus.FORBIDDEN, e.getStatus());
    }
  }

  @Test
  public void testDeletePostByIdInvalidPost() {
    //setup
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID postId = UUID.randomUUID();

    ClientEntity client = new ClientEntity(clientId, "testClient");
    UserEntity user = new UserEntity(userId, "testUser", "testUser", client);
    PostEntity postEntity = new PostEntity(postId, user, client);
    postEntity.setTitle("testPost");
    postEntity.setContent("testPost");
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");
    Token token = new Token(clientId);
    Boolean expectResponse = true;

    //when
    Mockito.when(postRepository.findByPostId(postId)).thenReturn(null);

    //test
    try {
      Boolean deleteResponse = postserviceimpl.deletePostById(postId, token);
    } catch (ResponseStatusException e) {
      Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
    }
  }

  @Test
  public void testDeletePostByIdInvalidComment() {
    //setup
    UUID clientId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID postId = UUID.randomUUID();
    UUID commentId = UUID.randomUUID();


    ClientEntity client = new ClientEntity(clientId, "testClient");
    UserEntity user = new UserEntity(userId, "testUser", "testUser", client);
    PostEntity postEntity = new PostEntity(postId, user, client);
    postEntity.setTitle("testPost");
    postEntity.setContent("testPost");
    Post post = new Post(postId, clientId, userId, "testPost", "testPost");
    Token token = new Token(clientId);
    Boolean expectResponse = true;

    CommentEntity commentEntity = new CommentEntity(commentId, user, client, postEntity);
    Set<CommentEntity> comments = new HashSet<>();
    comments.add(commentEntity);
    postEntity.setComments(comments);

    //when
    Mockito.when(postRepository.findByPostId(postId)).thenReturn(postEntity);

    //test
    try {
      Boolean deleteResponse = postserviceimpl.deletePostById(postId, token);
    } catch (ResponseStatusException e) {
      Assertions.assertEquals(HttpStatus.FORBIDDEN, e.getStatus());
    }
  }
}

