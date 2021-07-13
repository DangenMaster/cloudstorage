package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId)" +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int createNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userId=#{userId}")
    List<Note> getNotes(int userId);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId}")
    int deleteNote(int noteId);

    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, noteDescription=#{noteDescription} WHERE noteId=#{noteId}")
    int updateNote(Note note);

}
