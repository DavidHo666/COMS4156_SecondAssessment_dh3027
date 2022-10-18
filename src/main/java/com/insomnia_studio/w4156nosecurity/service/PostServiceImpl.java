package com.insomnia_studio.w4156nosecurity.service;

import com.insomnia_studio.w4156nosecurity.entity.PostEntity;
import com.insomnia_studio.w4156nosecurity.model.Post;
import com.insomnia_studio.w4156nosecurity.repository.PostEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private PostEntityRepository postEntityRepository;

    public PostServiceImpl(PostEntityRepository postEntityRepository) {
        this.postEntityRepository = postEntityRepository;
    }

    @Override
    public Post addPost(Post post) throws Exception {
        try {
            PostEntity postEntity = new PostEntity();
            BeanUtils.copyProperties(post, postEntity);
            postEntityRepository.save(postEntity);
            post.setPostId(postEntity.getPostId());
            post.setPostCreatedTime(postEntity.getPostCreatedTime());
            post.setPostUpdatedTime(postEntity.getPostUpdatedTime());
        } catch (Exception e) {
            throw new Exception("Could not save Post: " + e);
        }
        return post;
    }

    @Override
    public Optional<Post> getPostById(UUID postId) {
        PostEntity postEntity = postEntityRepository.findByPostId(postId).get();
        Post post = new Post();
        BeanUtils.copyProperties(postEntity, post);
        return Optional.of(post);
    }

    @Override
    public Optional<Post> updatePostById(UUID postId, Post post) throws Exception {
        try {
            PostEntity postEntity = postEntityRepository.findByPostId(postId).get();
            postEntity.setTitle(post.getTitle());
            postEntity.setContent(post.getContent());
            postEntity.setTags(post.getTags());
            postEntityRepository.save(postEntity);
            Post newPost = new Post();
            BeanUtils.copyProperties(postEntity, post);
        } catch (Exception e) {
            throw new Exception("Could not update Post: " + e);
        }
        return Optional.of(post);
    }

    @Override
    public Boolean deletePostById(UUID postId) {
        Boolean is_deleted = (postEntityRepository.deletePostEntityByPostId(postId) == 1);
        return is_deleted;
    }


}
