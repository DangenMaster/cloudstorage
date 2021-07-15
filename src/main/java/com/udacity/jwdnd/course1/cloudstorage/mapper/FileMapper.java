package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contentType, fileSize, userId, fileData)" +
            "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Select("SELECT * FROM FILES WHERE userId=#{userId}")
    List<File> getFiles(int userId);

    @Select("SELECT * FROM FILES WHERE filename=#{filename}")
    File getFileByName(String filename);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    int deleteFile(int fileId);
}
