package coding_platform;

public class Main {
    public static void main(String[] args) {
        String username = "kamal";
        String problemFile = "s3 key of the problem"; // S3 key

        // Fetch problem dynamically from S3
        Problem problem = ProblemBank.fetchProblem(problemFile);
        if (problem == null) {
            System.out.println("Failed to fetch problem.");
            return;
        }

        System.out.println("Problem: " + problem.getTitle());
        System.out.println(problem.getDescription());
        // Update leaderboard and upload submission
        SubmissionManager.updateLeaderboard(username, problem.getTitle(), score);
        SubmissionManager.uploadToS3("src/Main.java", username, problem.getTitle());
    }
}
