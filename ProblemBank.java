package coding_platform;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProblemBank {
    private static final String BUCKET_NAME = "your-s3-bucket-name";

    public static Problem fetchProblem(String problemFile) {
        try {
            S3Client s3 = S3Client.builder()
                    .region(Region.US_EAST_1)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();

            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(problemFile)
                    .build();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(s3.getObject(request))
            );

            StringBuilder desc = new StringBuilder();
            String title = reader.readLine(); // First line = title
            String line;
            while ((line = reader.readLine()) != null) {
                desc.append(line).append("\n");
            }

            reader.close();
            return new Problem(title, desc.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
