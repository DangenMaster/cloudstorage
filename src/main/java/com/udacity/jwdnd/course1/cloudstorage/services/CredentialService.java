package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public int insertCredential(Credential credential) {
        return credentialMapper.insertCredential(credential);
    }

    public List<Credential> getCredentials(int userId) {
        return credentialMapper.getCredentials(userId);
    }

    public int deleteCredential(int credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

    public int updateCredential(Credential credential) {
        return credentialMapper.updateCredential(credential);
    }
}
