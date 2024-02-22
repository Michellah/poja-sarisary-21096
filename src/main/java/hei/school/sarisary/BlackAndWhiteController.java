package hei.school.sarisary;
import hei.school.sarisary.file.BucketComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Duration;

@RestController
public class BlackAndWhiteController {

    private final BucketComponent bucketComponent;

    @Autowired
    public BlackAndWhiteController(BucketComponent bucketComponent) {
        this.bucketComponent = bucketComponent;
    }

    @PutMapping("/black-and-white/{id}")
    public ResponseEntity<Void> applyBlackAndWhiteFilter(@PathVariable String id, @RequestBody byte[] imageBytes) {
        try {
            File tempFile = File.createTempFile("image-", ".png");
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(imageBytes);
            fos.close();
            bucketComponent.uploadFile(tempFile, id);

            tempFile.delete();

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/black-and-white/{id}")
    public ResponseEntity<Object> getBlackAndWhiteUrls(@PathVariable String id) {
        try {
            String originalUrl = bucketComponent.presign(id + "/original.png", Duration.ofMinutes(5)).toString();
            String transformedUrl = bucketComponent.presign(id + "/transformed.png", Duration.ofMinutes(5)).toString();
            String responseBody = "{\"original_url\": \"" + originalUrl + "\", \"transformed_url\": \"" + transformedUrl + "\"}";

            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
