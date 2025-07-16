package Lectra.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@Service
public class S3Service {

    @Value("${aws.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    public HttpStatus uploadFile(MultipartFile multipartFile){
        UUID key= UUID.randomUUID();
        try{
            PutObjectRequest request = new PutObjectRequest(bucketName, key.toString(), multipartFile.getInputStream(), null);
            amazonS3.putObject(request);
            return HttpStatus.OK;
        }catch(IOException e){
            return HttpStatus.CONFLICT;
        }
    }

    public Optional<String> getURL(String key){
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName,key);
        URL url = amazonS3.generatePresignedUrl(request);
        return Optional.of(url.toString());
    }
}
