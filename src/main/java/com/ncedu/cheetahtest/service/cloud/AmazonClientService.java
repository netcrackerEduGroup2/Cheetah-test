package com.ncedu.cheetahtest.service.cloud;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ncedu.cheetahtest.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AmazonClientService {
    private AmazonS3 s3client;
    private final UserService userService;

    @Value("${cloud.aws.endpointUrl}")
    private String endpointUrl;
    @Value("${cloud.aws.bucketName}")
    private String bucketName;
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    // its maximum permitted size of 20971520 bytes.
    public String uploadUserPhoto(MultipartFile multipartFile, int id) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = "https://"+bucketName+endpointUrl+"/userPhoto/"+fileName;
            uploadFileTos3bucket("userPhoto/"+fileName, file);
            file.delete();
            userService.setUserPhotoUrl(id, fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    // its maximum permitted size of 20971520 bytes.
    public String uploadScreenshot(File file) {
        String fileUrl = "";
        try {
            String fileName = generateFileName(file);
            fileUrl = "https://"+bucketName+endpointUrl+"/screenshots/"+fileName;
            uploadFileTos3bucket("screenshots/"+fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    public File convertMultiPartToFile(MultipartFile file) throws IOException { //TODO return to private
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private String generateFileName(File file) {
        return new Date().getTime() + "-" + file.getName().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String deleteUserPhotoFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, "userPhoto/"+fileName));
        return "Successfully deleted";
    }

    public String deleteScreenshotFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, "screenshots/"+fileName));
        return "Successfully deleted";
    }
}
