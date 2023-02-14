package com.example.vk.Controllers;

import com.example.vk.DTO.postsDto.PostDto;
import com.example.vk.DTO.postsDto.PostUpdateDto;
import com.example.vk.Response.ResponseDto;
import com.example.vk.Service.Implaye.PostServiceImpl;
import com.example.vk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping()
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto){
        return ResponseEntity.ok(ResponseDto.builder().data(postService.savePost(postDto)).build());
    }

    @GetMapping()
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getNews(@RequestParam("id") Long id, @RequestParam("page") Long page){
        if (id == null){
            throw new NotFoundException("Don't found news");
        }
        log.info("Get news");
        return ResponseEntity.ok(ResponseDto.builder().data(postService.getNews(id, page)).build());
    }

    @PatchMapping("/post")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> patchPost(@RequestBody PostUpdateDto postUpdateDto){
        if(postUpdateDto == null){
            throw new NotFoundException("Don't found post");
        }
        log.info("Patch post");
        return ResponseEntity.ok(ResponseDto.builder().data(postService.updatePost(postUpdateDto)).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> deletePostById(@PathVariable("id") Long id){
        if (id == null){
            throw new NotFoundException("Don't found post");
        }
        log.info("Get post");
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
