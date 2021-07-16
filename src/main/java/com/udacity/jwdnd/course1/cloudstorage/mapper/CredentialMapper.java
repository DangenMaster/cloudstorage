package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId)" +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId}")
    List<Credential> getCredentials(int userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    int deleteCredential(int credentialId);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialId=#{credentialId}")
    int updateCredential(Credential credential);
}
