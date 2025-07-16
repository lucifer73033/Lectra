package Lectra.S3;

import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    HttpStatus uploadFile(@RequestParam MultipartFile file){
        HttpStatus response = s3Service.uploadFile(file);
        return response;
    }

    @GetMapping("/get")
    ResponseEntity<String> getFileURL(@RequestParam String key){
        Optional response=s3Service.getURL(key);
        return response.isPresent()?ResponseEntity.status(200).body((String)response.get()):ResponseEntity.status(500).body("Error");
    }
}
