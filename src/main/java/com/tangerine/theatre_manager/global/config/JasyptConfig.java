package com.tangerine.theatre_manager.global.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jasypt.encryptor")
@EnableEncryptableProperties
public class JasyptConfig {

    private final String PATH = "src/main/resources/encryptor_info.txt";

    private String algorithm;
    private int poolSize;
    private String stringOutputType;
    private int keyObtentionIterations;

    private PooledPBEStringEncryptor encryptor;
    private String encryptorPassword;
    private String datasourceUrl;
    private String datasourceUsername;
    private String datasourcePassword;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {
        encryptor = new PooledPBEStringEncryptor();
        readJasyptEncryptorFile();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setAlgorithm(algorithm);
        config.setPoolSize(poolSize);
        config.setStringOutputType(stringOutputType);
        config.setKeyObtentionIterations(keyObtentionIterations);
        config.setPassword(encryptorPassword);
        encryptor.setConfig(config);
        return encryptor;
    }

    public void readJasyptEncryptorFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            encryptorPassword = br.readLine();
            datasourceUrl = br.readLine();
            datasourceUsername = br.readLine();
            datasourcePassword = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Encryptor Info File not found", e);
        }
    }

    public String jasyptEncrypt(String input) {
        return encryptor.encrypt(input);
    }

    public String jasyptDecrypt(String input) {
        return encryptor.decrypt(input);
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public String getStringOutputType() {
        return stringOutputType;
    }

    public void setStringOutputType(String stringOutputType) {
        this.stringOutputType = stringOutputType;
    }

    public int getKeyObtentionIterations() {
        return keyObtentionIterations;
    }

    public void setKeyObtentionIterations(int keyObtentionIterations) {
        this.keyObtentionIterations = keyObtentionIterations;
    }

    public String getEncryptorPassword() {
        return encryptorPassword;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public String getDatasourceUsername() {
        return datasourceUsername;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }
}
