# Part 1B
Dawei He

I have added a Token class in directory src/main/java/com/insomnia_studio/w4156pj/model. This class is used to only
receive the clientId when client perform Get or Delete post request. So that the Post class 
(src/main/java/com/insomnia_studio/w4156pj/model/Post.java) is not used for receiving clientId and passing it to 
authentication.


Steps:
1. Add a new Token class in the directory src/main/java/com/insomnia_studio/w4156pj/model with only one field UUID 
clientId and a constructor.
````
public class Token {
  private UUID clientId;

  public Token(UUID clientId) {
    this.clientId = clientId;
  }
}
````

2. Delete the following constructor that only initializing the clientId field in 
src/main/java/com/insomnia_studio/w4156pj/model/Post.java
````
  public Post(UUID clientId) {
    this.clientId = clientId;
  }
````

3. Change the the Get method and Delete method in PostController from:
````
   public Post getPostByPostId(@PathVariable UUID postId, @RequestBody Post post) throws Exception {
        return postService.getPostById(postId, post);
   }
   
     public Map<String, Boolean> deletePostByPostId(@PathVariable UUID postId, @RequestBody Post post)
          throws Exception {
        Map<String, Boolean> response = new HashMap<>();
        boolean is_deleted = (postService.deletePostById(postId, post));
        response.put("Deleted", is_deleted);
        return response;
  }
````
to:
````
   public Post getPostByPostId(@PathVariable UUID postId, @RequestBody Token token) throws Exception {
        return postService.getPostById(postId, token);
   }
   
     public Map<String, Boolean> deletePostByPostId(@PathVariable UUID postId, @RequestBody Token token)
          throws Exception {
        Map<String, Boolean> response = new HashMap<>();
        boolean is_deleted = (postService.deletePostById(postId, token));
        response.put("Deleted", is_deleted);
        return response;
  }
````
4. Change method signature of getPostById and deletePostById in both PostService interface 
(src/main/java/com/insomnia_studio/w4156pj/service/PostService.java) and PostService implementationPost
(src/main/java/com/insomnia_studio/w4156pj/service/PostServiceImpl.java) from:
````
getPostById(UUID postId, Post post) throws Exception;
Boolean deletePostById(UUID postId, Post post) throws Exception;
````
to:
````
getPostById(UUID postId, Token token) throws Exception;
Boolean deletePostById(UUID postId, Token token) throws Exception;
````
5. In the implementation of method getPostById and deletePostById, all variables that use Post object to store 
clientId are changed to token. So that we can get clientId and verify it by calling Token object instead of Post Object. 
Therefore, for both classes Post and Token, they only have one singular responsibility.

Before:
````
  public Post getPostById(UUID postId, Post post) throws ResponseStatusException {
    PostEntity postEntity = postEntityRepository.findByPostId(postId);
    if (postEntity != null) {
      if (postEntity.getClient().getClientId().compareTo(post.getClientId()) != 0) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
      }
      Post responsePost = new Post();
      BeanUtils.copyProperties(postEntity, responsePost);
      responsePost.setUserId(postEntity.getUser().getUserId());
      responsePost.setClientId(postEntity.getClient().getClientId());
      return responsePost;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post ID not found");
    }
  }
  
  public Boolean deletePostById(UUID postId, Post post) throws ResponseStatusException {
    PostEntity postEntity = postEntityRepository.findByPostId(postId);
    if (postEntity != null) {
      if (postEntity.getClient().getClientId().compareTo(post.getClientId()) != 0) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
      }
      if (postEntity.getComments().size() > 0) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "The post has comments, can't be deleted.");
      }
      Boolean is_deleted = (postEntityRepository.deletePostEntityByPostId(postId) == 1);
      return is_deleted;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post ID not found");
    }
  }
 ````

After:
````
  public Post getPostById(UUID postId, Token token) throws ResponseStatusException {
    PostEntity postEntity = postEntityRepository.findByPostId(postId);
    if (postEntity != null) {
      if (postEntity.getClient().getClientId().compareTo(token.getClientId()) != 0) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
      }
      Post responsePost = new Post();
      BeanUtils.copyProperties(postEntity, responsePost);
      responsePost.setUserId(postEntity.getUser().getUserId());
      responsePost.setClientId(postEntity.getClient().getClientId());
      return responsePost;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post ID not found");
    }
  }
  
  public Boolean deletePostById(UUID postId, Token token) throws ResponseStatusException {
    PostEntity postEntity = postEntityRepository.findByPostId(postId);
    if (postEntity != null) {
      if (postEntity.getClient().getClientId().compareTo(token.getClientId()) != 0) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Client ID");
      }
      if (postEntity.getComments().size() > 0) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "The post has comments, can't be deleted.");
      }
      Boolean is_deleted = (postEntityRepository.deletePostEntityByPostId(postId) == 1);
      return is_deleted;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post ID not found");
    }
  }
 ````


6. For unit test in src/test/java/com/insomnia_studio/w4156pj/controller/PostControllerTest.java and 
src/test/java/com/insomnia_studio/w4156pj/service/PostServiceImplTest.java, all variables that use post to store clientId
   are also changed to Token object, which is very similar to the previous step.

