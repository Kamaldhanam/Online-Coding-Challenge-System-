package coding_platform;

public class Main {
    public static void main(String[] args) {
        String username = "testuser";
        String problemFile = "problem1.txt"; // S3 key

        // Fetch problem dynamically from S3
        Problem problem = ProblemBank.fetchProblem(problemFile);
        if (problem == null) {
            System.out.println("Failed to fetch problem.");
            return;
        }

        System.out.println("Problem: " + problem.getTitle());
        System.out.println(problem.getDescription());

        // Example DSA Solution: Reverse an array
        int[] arr = {1, 2, 3, 4, 5};
        System.out.print("Reversed array: ");
        int score = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            System.out.print(arr[i] + " ");
            score += arr[i]; // Dummy score based on sum
        }
        System.out.println();

        // Update leaderboard and upload submission
        SubmissionManager.updateLeaderboard(username, problem.getTitle(), score);
        SubmissionManager.uploadToS3("src/Main.java", username, problem.getTitle());
    }
}
