package com.dev.workhiveback.mappers;

import com.dev.workhiveback.entities.ImageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface ImageMapper {
    int insert(ImageEntity image);

    ImageEntity selectByIndex(@Param(value = "index") int index);

}
