package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean isFilenameExist(String filename) {
        return fileMapper.getFileByName(filename) == null;
    }

    public int insertFile(File file) {
        return fileMapper.insertFile(file);
    }

    public List<File> getFiles(int userId) {
        return fileMapper.getFiles(userId);
    }

    public File getFile(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    public int deleteFile(int fileId) {
        return fileMapper.deleteFile(fileId);
    }
}
