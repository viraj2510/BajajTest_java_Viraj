import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashGenerator {
    public static void main(String[] args) {
        try {
            // Load and parse JSON file
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new FileInputStream("input.json");
            JsonNode rootNode = mapper.readTree(inputStream);

            // Extract student details
            String fName = rootNode.path("student").path("first_name").asText().trim().toLowerCase();
            String rollNo = rootNode.path("student").path("roll_number").asText().trim();
            
            // Generate a secure hash
            String resultHash = computeHash(fName + rollNo);
            
            // Save the output
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
                writer.write(resultHash);
            }

            System.out.println("Hash Generated: " + resultHash);
        } catch (Exception ex) {
            System.err.println("Error processing file: " + ex.getMessage());
        }
    }

    // Custom method to compute MD5 hash
    public static String computeHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            return "Error: Hashing failed";
        }
    }
}
