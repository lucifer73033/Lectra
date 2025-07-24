package Lectra.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.NoSuchObjectException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class S3Service {

    private final int BUFFERSIZE=5242880;

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
            ObjectMetadata metadata=new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            PutObjectRequest request = new PutObjectRequest(bucketName, key.toString(), multipartFile.getInputStream(), metadata);
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

    public HttpStatus multipartUploadFileTest(MultipartFile file)throws IOException{
        UUID key = UUID.randomUUID();
//        System.out.println(file.getSize());
        InitiateMultipartUploadResult uploadResult=amazonS3.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucketName,key.toString()));
        String multipartID = uploadResult.getUploadId();
        UploadPartRequest request = new UploadPartRequest();
        request = request.withBucketName(bucketName)
                .withInputStream(file.getInputStream())
                .withPartNumber(1)
                .withUploadId(multipartID)
                .withKey(key.toString())
                .withPartSize(file.getSize());
        UploadPartResult partResult = amazonS3.uploadPart(request);
        List<PartETag> ETTags = new ArrayList<>();
        ETTags.add(partResult.getPartETag());
        System.out.println("ET Tag: "+partResult.getETag());
        PartListing listing= amazonS3.listParts(new ListPartsRequest(bucketName,key.toString(),multipartID));
        List<PartSummary> list=listing.getParts();
        list.stream().map(x->String.valueOf(x.getSize())+" "+x.getETag()).forEach(System.out::println);
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName,key.toString(),uploadResult.getUploadId(),ETTags);
        CompleteMultipartUploadResult completeMultipartUploadResult=amazonS3.completeMultipartUpload(completeMultipartUploadRequest);
        return HttpStatus.OK;
    }

    public HttpStatus multipartUploadFile(MultipartFile file)throws IOException{
        List<byte[]> fileList=getList(file);
//        fileList.forEach(System.out :: println);
        String key = UUID.randomUUID().toString();
        String multipartID = amazonS3.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucketName,key)).getUploadId();
        List<PartETag> ETags = concurrentMultipartUpload(fileList,key,multipartID);
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, multipartID, ETags);
        CompleteMultipartUploadResult completeMultipartUploadResult = amazonS3.completeMultipartUpload(completeMultipartUploadRequest);
        return HttpStatus.ACCEPTED;
    }

    private List<PartETag> concurrentMultipartUpload(List<byte[]> fileList, String key, String multipartID) {
        int i=1;
        List<PartETag> Etags = new ArrayList<>();
        List<CompletableFuture<UploadPartResult>> completableFutures = new ArrayList<>();
        try{
            for (byte[] stream : fileList) {
                UploadPartRequest request = new UploadPartRequest()
                        .withPartSize(stream.length)
                        .withKey(key)
                        .withUploadId(multipartID)
                        .withPartNumber(i++)
                        .withBucketName(bucketName)
                        .withInputStream(new ByteArrayInputStream(stream));
                CompletableFuture<UploadPartResult> result = CompletableFuture.supplyAsync(()->amazonS3.uploadPart(request));
                completableFutures.add(result);
            }
            Etags = completableFutures.stream()
                    .map(CompletableFuture::join)
                    .sorted((a,b)->(a.getPartNumber()-b.getPartNumber()))
                    .map(UploadPartResult::getPartETag)
                    .collect(Collectors.toList());

        }catch(Exception e){
            System.out.println("--------->>>BOOHOOOO: "+e.getMessage());
        }
        return Etags;
    }

    private List<byte[]> getList(MultipartFile file) {
        List<byte[]> list = new ArrayList<>();
        try(InputStream stream = file.getInputStream()){
            byte[] buffer = new byte[BUFFERSIZE];
            int dataRead = stream.read(buffer);
            while(dataRead > -1){
                list.add(buffer);
                buffer= new byte[BUFFERSIZE];
                dataRead = stream.read(buffer, 0, dataRead);
            }
        }catch(IOException e){
            System.out.println("---------------->BOOOOOOOOHOOOOOOOOOOO");
        }
        return list;
    }
}
