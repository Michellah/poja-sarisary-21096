package hei.school.sarisary;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.io.File;
import java.io.IOException;
import hei.school.sarisary.file.BucketComponent;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
public class BlackAndWhiteControllerTest {
        @Test
        public void testApplyBlackAndWhiteFilter() throws IOException {
            BucketComponent bucketComponent = mock(BucketComponent.class);
            BlackAndWhiteController controller = new BlackAndWhiteController(bucketComponent);
            byte[] imageBytes = new byte[10];
            ResponseEntity<Void> response = controller.applyBlackAndWhiteFilter("testId", imageBytes);
            verify(bucketComponent).uploadFile(any(File.class), any(String.class));
            assert response.getStatusCode().equals(HttpStatus.OK);
        }
}
