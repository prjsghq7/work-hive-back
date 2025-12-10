package com.dev.workhiveback.results.reasons.user;

import com.dev.workhiveback.dtos.user.UserSearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchResult {
    // TODO : 페이징 처리 공용 작업 후 추가
    // PageVo pageVo;
    
    List<UserSearchDto> userList;
}
