package Lectra.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.NoSuchObjectException;
import java.util.Optional;
import java.util.UUID;

@Service
public class S3Service {

    @Value("${aws.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private HttpServletRequest request;

    public HttpStatus uploadFile(MultipartFile multipartFile) throws IOException{
        UUID key= UUID.randomUUID();
        //CHECKING IF FILE IS RECEIVED PROPERLY
//        Path rootLocation=Paths.get("upload-dir");
//        Path destinationFile=rootLocation.resolve(Paths.get(multipartFile.getOriginalFilename()))
//                .normalize()
//                .toAbsolutePath();
//        InputStream inputStream=multipartFile.getInputStream();
//        Files.copy(inputStream,destinationFile, StandardCopyOption.REPLACE_EXISTING);

        try{
            PutObjectRequest request = new PutObjectRequest(bucketName, key.toString(), multipartFile.getInputStream(), null);
            amazonS3.putObject(request);
            return HttpStatus.OK;
        }catch(IOException e){
            System.out.println(e.getMessage());
            return HttpStatus.CONFLICT;
        }
    }

    public Optional<String> getURL(String key){
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName,key);
        URL url = amazonS3.generatePresignedUrl(request);
        return Optional.of(url.toString());
    }
}
