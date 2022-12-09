package com.insomnia_studio.w4156pj.fixmutation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insomnia_studio.w4156pj.controller.CommentController;
import com.insomnia_studio.w4156pj.controller.PostController;
import com.insomnia_studio.w4156pj.controller.UserController;
import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.entity.CommentEntity;
import com.insomnia_studio.w4156pj.entity.PostEntity;
import com.insomnia_studio.w4156pj.entity.UserEntity;
import com.insomnia_studio.w4156pj.model.Client;
import com.insomnia_studio.w4156pj.model.Comment;
import com.insomnia_studio.w4156pj.model.Post;
import com.insomnia_studio.w4156pj.model.User;
import com.insomnia_studio.w4156pj.repository.ClientEntityRepository;
import com.insomnia_studio.w4156pj.repository.CommentEntityRepository;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import com.insomnia_studio.w4156pj.repository.UserEntityRepository;
import com.insomnia_studio.w4156pj.service.ClientService;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class FixMutationTest {
  static UUID testClientId;
  //	UUID testUserId = UUID.fromString("5911dcbb-4af7-4fcf-ba3c-62af503d4dc1");
  static UUID testUserId;
  //	UUID testPostId = UUID.fromString("440fa679-93fa-4c27-aa7f-b76c02988c65");
  static UUID testPostId;
  static UUID testCommentId;
  static UUID fakeCommentId = UUID.fromString("26878fd1-ad62-46ec-98cf-0b339c35d2ca");
  private final String testClientName = "testClient";
  UUID fakeClientId = UUID.fromString("2235566f-a37f-4b5f-a0d9-6961689c46c2");
  UUID fakeUserId = UUID.fromString("5911dcbb-4af7-4fcf-ba3c-62af503d4dc2");
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ClientEntityRepository clientEntityRepository;
  @Autowired
  private UserEntityRepository userEntityRepository;
  @Autowired
  private PostEntityRepository postEntityRepository;
  @Autowired
  private CommentEntityRepository commentEntityRepository;
  @Autowired
  private UserController userController;
  @Autowired
  private PostController postController;
  @Autowired
  private CommentController commentController;
  @Autowired
  private ClientService clientService;

  @Test
  void testDeleteUserValidClient() throws Exception {
    Client client = new Client(this.testClientName);
    ClientEntity clientEntity = new ClientEntity();
    BeanUtils.copyProperties(client, clientEntity);
    clientEntity = clientEntityRepository.save(clientEntity);
    testClientId = clientEntity.getClientId();
    UserEntity userEntity = new UserEntity();
    User user = new User(testClientId, "test", "user");
    BeanUtils.copyProperties(user, userEntity);
    userEntity.setClient(clientEntity);
    userEntity = userEntityRepository.save(userEntity);
    User userOnlyClientId = new User(testClientId);

    // Delete the post
    mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/user/".concat(userEntity.getUserId().toString()))
            .content(new ObjectMapper().writeValueAsString(userOnlyClientId))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$", Matchers.hasValue(true)));
  }

}
