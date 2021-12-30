package com.example.reddisclone.service;

import com.example.reddisclone.dto.SubredditDto;
import com.example.reddisclone.entity.Subreddit;
import com.example.reddisclone.exception.RedditCloneExcption;
import com.example.reddisclone.mapper.SubredditMapper;
import com.example.reddisclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        List<SubredditDto> collect = subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
        return collect;
    }


    public SubredditDto getSubreddit(Long id) {
        Subreddit  subreddit= subredditRepository.findById(id)
                .orElseThrow(()-> new RedditCloneExcption("No subreddit found with id "+id));
                return subredditMapper.mapSubredditToDto(subreddit);
    }
}
