package coding_platform;

import java.io.File;
import java.sql.*;
import java.util.*;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class SubmissionManager {

    private static final String BUCKET_NAME = "kamals-s3-bucketname";

    public static void uploadToS3(String filePath, String username, String problemTitle) {
        try {
            S3Client s3 = S3Client.builder()
                    .region(Region.US_EAST_1)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();

            File file = new File(filePath);
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(username + "/" + problemTitle + "/" + file.getName())
                    .build();

            s3.putObject(request, file.toPath());
            System.out.println("Uploaded submission to S3: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int evaluateSubmission(String codeFilePath, String inputFile, String expectedOutputFile) throws Exception {
    // 1. Compile user code
    Process compile = new ProcessBuilder("javac", codeFilePath).start();
    compile.waitFor();

    // 2. Run code with input
    Process run = new ProcessBuilder("java", "Main")
        .redirectInput(new File(inputFile))
        .redirectOutput(new File("user_output.txt"))
        .start();
    run.waitFor();

    // 3. Compare output with expected
    List<String> userOutput = Files.readAllLines(Paths.get("user_output.txt"));
    List<String> expectedOutput = Files.readAllLines(Paths.get(expectedOutputFile));

    if (userOutput.equals(expectedOutput)) {
        return 100; // correct
    } else {
        return 0;   // wrong answer
    }
}
    int score = evaluateSubmission("src/Main.java", "input.txt", "expected_output.txt");
SubmissionManager.updateLeaderboard(username, problem.getTitle(), score);
    public static void updateLeaderboard(String username, String problemTitle, int score) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/coding_platform", "kamal", "kamal@123"
            );

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO leaderboard (username, problem_title, score) VALUES (?, ?, ?)"
            );
            stmt.setString(1, username);
            stmt.setString(2, problemTitle);
            stmt.setInt(3, score);
            stmt.executeUpdate();

            conn.close();
            System.out.println("Leaderboard updated for " + username);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
