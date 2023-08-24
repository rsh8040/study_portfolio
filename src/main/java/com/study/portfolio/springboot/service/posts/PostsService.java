package com.study.portfolio.springboot.service.posts;

import com.study.portfolio.springboot.domain.posts.Posts;
import com.study.portfolio.springboot.domain.posts.PostsRepository;
import com.study.portfolio.springboot.web.dto.PostsResponseDto;
import com.study.portfolio.springboot.web.dto.PostsSaveRequestDto;
import com.study.portfolio.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalIdentifierException("해당게시글이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }
}
