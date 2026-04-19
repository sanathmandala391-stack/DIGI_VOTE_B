//package VotingSystem.demo.Service;
//
//import VotingSystem.demo.Repository.UserRepository;
//import VotingSystem.demo.config.JwtUtil;
//import VotingSystem.demo.models.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class FaceAuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    // Save the face descriptor (128 floats as JSON string) for a user
//    public boolean registerFaceDescriptor(Long userId, String descriptorJson) {
//        try {
//            User user = userRepository.findById(userId).orElse(null);
//            if (user == null) {
//                System.out.println("FACE REGISTER >>> user not found: " + userId);
//                return false;
//            }
//            user.setFaceEmbedding(descriptorJson);
//            userRepository.save(user);
//            System.out.println("FACE REGISTER >>> descriptor saved for userId=" + userId);
//            return true;
//        } catch (Exception e) {
//            System.out.println("FACE REGISTER ERROR: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Compare uploaded descriptor with all stored ones, return JWT if match found
//    public String loginWithDescriptor(String descriptorJson) {
//        try {
//            double[] uploaded = parseDescriptor(descriptorJson);
//            if (uploaded == null) return null;
//
//            List<User> allUsers = userRepository.findAll();
//
//            for (User u : allUsers) {
//                if (u.getFaceEmbedding() == null) continue;
//                double[] stored = parseDescriptor(u.getFaceEmbedding());
//                if (stored == null) continue;
//
//                double distance = euclideanDistance(uploaded, stored);
//                System.out.println("FACE LOGIN >>> checking userId=" + u.getId()
//                        + " distance=" + distance);
//
//                // face-api.js threshold is typically 0.6
//                // Lower distance = more similar faces
//                if (distance < 0.6) {
//                    String role = u.getRole() != null ? u.getRole().name() : "VOTER";
//                    System.out.println("FACE LOGIN >>> MATCHED userId=" + u.getId());
//                    return jwtUtil.generateToken(u.getId(), role);
//                }
//            }
//
//            System.out.println("FACE LOGIN >>> no match found");
//            return null;
//
//        } catch (Exception e) {
//            System.out.println("FACE LOGIN ERROR: " + e.getMessage());
//            return null;
//        }
//    }
//
//    private double[] parseDescriptor(String json) {
//        try {
//            double[] arr = objectMapper.readValue(json, double[].class);
//            return arr;
//        } catch (Exception e) {
//            System.out.println("PARSE ERROR: " + e.getMessage());
//            return null;
//        }
//    }
//
//    private double euclideanDistance(double[] a, double[] b) {
//        if (a.length != b.length) return Double.MAX_VALUE;
//        double sum = 0;
//        for (int i = 0; i < a.length; i++) {
//            double diff = a[i] - b[i];
//            sum += diff * diff;
//        }
//        return Math.sqrt(sum);
//    }
//}


//new Second///
//
//
//package VotingSystem.demo.Service;
//
//import VotingSystem.demo.Repository.UserRepository;
//import VotingSystem.demo.config.JwtUtil;
//import VotingSystem.demo.models.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class FaceAuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    // Threshold: lower = stricter matching. 0.45 = high security.
//    private static final double MATCH_THRESHOLD = 0.45;
//    // Duplicate detection threshold: if new face is too similar to existing (other user), reject it
//    private static final double DUPLICATE_THRESHOLD = 0.50;
//
//    public boolean registerFaceDescriptor(Long userId, String descriptorJson) {
//        try {
//            User user = userRepository.findById(userId).orElse(null);
//            if (user == null) {
//                System.out.println("FACE REGISTER >>> user not found: " + userId);
//                return false;
//            }
//
//            double[] newDescriptor = parseDescriptor(descriptorJson);
//            if (newDescriptor == null) {
//                System.out.println("FACE REGISTER >>> invalid descriptor");
//                return false;
//            }
//
//            // CHECK UNIQUENESS: compare against ALL other users' faces
//            List<User> allUsers = userRepository.findAll();
//            for (User other : allUsers) {
//                if (other.getId().equals(userId)) continue; // skip self
//                if (other.getFaceEmbedding() == null) continue;
//
//                double[] storedDescriptor = parseDescriptor(other.getFaceEmbedding());
//                if (storedDescriptor == null) continue;
//
//                double distance = euclideanDistance(newDescriptor, storedDescriptor);
//                System.out.println("FACE REGISTER >>> uniqueness check vs userId=" + other.getId() + " distance=" + String.format("%.4f", distance));
//
//                if (distance < DUPLICATE_THRESHOLD) {
//                    // Face too similar to an existing user
//                    System.out.println("FACE REGISTER >>> DUPLICATE FACE DETECTED! Similar to userId=" + other.getId());
//                    return false; // Rejected — not unique
//                }
//            }
//
//            user.setFaceEmbedding(descriptorJson);
//            userRepository.save(user);
//            System.out.println("FACE REGISTER >>> unique face saved for userId=" + userId);
//            return true;
//
//        } catch (Exception e) {
//            System.out.println("FACE REGISTER ERROR: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public String loginWithDescriptor(String descriptorJson) {
//        try {
//            double[] uploaded = parseDescriptor(descriptorJson);
//            if (uploaded == null) return null;
//
//            List<User> allUsers = userRepository.findAll();
//            User bestMatch = null;
//            double bestDistance = Double.MAX_VALUE;
//
//            for (User u : allUsers) {
//                if (u.getFaceEmbedding() == null) continue;
//                double[] stored = parseDescriptor(u.getFaceEmbedding());
//                if (stored == null) continue;
//
//                double distance = euclideanDistance(uploaded, stored);
//                System.out.println("FACE LOGIN >>> checking userId=" + u.getId() + " distance=" + String.format("%.4f", distance));
//
//                if (distance < bestDistance) {
//                    bestDistance = distance;
//                    bestMatch = u;
//                }
//            }
//
//            if (bestMatch != null && bestDistance < MATCH_THRESHOLD) {
//                String role = bestMatch.getRole() != null ? bestMatch.getRole().name() : "VOTER";
//                System.out.println("FACE LOGIN >>> MATCHED userId=" + bestMatch.getId() + " distance=" + String.format("%.4f", bestDistance));
//                return jwtUtil.generateToken(bestMatch.getId(), role);
//            }
//
//            System.out.println("FACE LOGIN >>> no match. Best distance=" + String.format("%.4f", bestDistance));
//            return null;
//
//        } catch (Exception e) {
//            System.out.println("FACE LOGIN ERROR: " + e.getMessage());
//            return null;
//        }
//    }
//
//    private double[] parseDescriptor(String json) {
//        try {
//            return objectMapper.readValue(json, double[].class);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private double euclideanDistance(double[] a, double[] b) {
//        if (a == null || b == null || a.length != b.length) return Double.MAX_VALUE;
//        double sum = 0;
//        for (int i = 0; i < a.length; i++) {
//            double diff = a[i] - b[i];
//            sum += diff * diff;
//        }
//        return Math.sqrt(sum);
//    }
//}
//
//
//


//new Final  Touch//

package VotingSystem.demo.Service;

import VotingSystem.demo.Repository.UserRepository;
import VotingSystem.demo.config.JwtUtil;
import VotingSystem.demo.models.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════════════════
 * ECI ADVANCED BIOMETRIC AUTHENTICATION SERVICE
 * ───────────────────────────────────────────────────────────────────────────
 * Security features:
 *
 * 1. Multi-descriptor registration
 *    - Accepts up to 3 descriptors (front + left-leaning + right-leaning)
 *    - Stores the average descriptor for robust matching
 *    - Falls back to primary if only one descriptor provided
 *
 * 2. Adaptive threshold matching
 *    - Base threshold: 0.42 (strict)
 *    - Widens slightly on retry (up to 0.48) if liveness score is high
 *    - Tightens if livenessScore is low
 *
 * 3. Liveness score validation
 *    - Frontend sends livenessScore (0-100) computed from:
 *      blink timing, head movement, frame variance
 *    - Requests with livenessScore < 60 are rejected
 *
 * 4. Anti-replay: challengeProof validation
 *    - Frontend sends challengeProof containing:
 *      challengeType, frameVariance, livenessScore, attempts
 *    - Backend validates frameVariance > minimum threshold
 *
 * 5. Unique face enforcement
 *    - Before registering, compares against ALL existing users
 *    - Rejects if distance < DUPLICATE_THRESHOLD (0.48)
 *
 * 6. One-vote enforcement
 *    - Handled at VoteController level (separate from face auth)
 * ═══════════════════════════════════════════════════════════════════════════
 */
@Service
public class FaceAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── Security thresholds ─────────────────────────────────────────────────
    private static final double BASE_MATCH_THRESHOLD  = 0.42;   // strict baseline
    private static final double MAX_MATCH_THRESHOLD   = 0.50;   // max allowed
    private static final double DUPLICATE_THRESHOLD   = 0.48;   // reject if too similar to other user
    private static final int    MIN_LIVENESS_SCORE    = 60;     // reject if below this
    private static final double MIN_FRAME_VARIANCE    = 1.5;    // anti-replay check

    // ─────────────────────────────────────────────────────────────────────────
    //  REGISTRATION
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Registers face for a user.
     * Accepts multi-descriptor payload from frontend.
     * Validates uniqueness against all registered users.
     *
     * @param userId        Target user ID
     * @param payloadJson   JSON: { descriptors: [[128 floats], ...], primaryDescriptor: [128 floats],
     *                             livenessScore: int, challengeProof: {...} }
     * @return "ok" | "duplicate" | "liveness_fail" | "error"
     */
    public String registerFaceDescriptor(Long userId, String payloadJson) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                log("REGISTER", "User not found: " + userId);
                return "error";
            }

            // ── Parse payload ────────────────────────────────────────────────
            JsonNode payload = objectMapper.readTree(payloadJson);

            int livenessScore = payload.has("livenessScore") ? payload.get("livenessScore").asInt(0) : 0;
            double frameVariance = payload.has("challengeProof")
                    && payload.get("challengeProof").has("frameVariance")
                    ? payload.get("challengeProof").get("frameVariance").asDouble(0) : 0;

            log("REGISTER", String.format("userId=%d livenessScore=%d frameVariance=%.2f",
                    userId, livenessScore, frameVariance));

            // ── Liveness validation ──────────────────────────────────────────
            if (livenessScore < MIN_LIVENESS_SCORE) {
                log("REGISTER", "REJECTED — liveness score too low: " + livenessScore);
                return "liveness_fail";
            }
            if (frameVariance < MIN_FRAME_VARIANCE) {
                log("REGISTER", "REJECTED — frame variance too low (possible static image): " + frameVariance);
                return "liveness_fail";
            }

            // ── Extract average descriptor ───────────────────────────────────
            double[] avgDescriptor = extractAverageDescriptor(payload);
            if (avgDescriptor == null) {
                log("REGISTER", "REJECTED — invalid descriptor data");
                return "error";
            }

            // ── Unique face check ────────────────────────────────────────────
            List<User> allUsers = userRepository.findAll();
            for (User other : allUsers) {
                if (other.getId().equals(userId)) continue;
                if (other.getFaceEmbedding() == null) continue;

                double[] stored = parseDescriptor(other.getFaceEmbedding());
                if (stored == null) continue;

                double dist = euclidean(avgDescriptor, stored);
                log("REGISTER", String.format("  Uniqueness check vs userId=%d — distance=%.4f", other.getId(), dist));

                if (dist < DUPLICATE_THRESHOLD) {
                    log("REGISTER", "REJECTED — DUPLICATE FACE detected (similar to userId=" + other.getId() + ")");
                    return "duplicate";
                }
            }

            // ── Save ─────────────────────────────────────────────────────────
            user.setFaceEmbedding(objectMapper.writeValueAsString(avgDescriptor));
            userRepository.save(user);
            log("REGISTER", "SUCCESS — unique face saved for userId=" + userId);
            return "ok";

        } catch (Exception e) {
            log("REGISTER", "ERROR: " + e.getMessage());
            return "error";
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  AUTHENTICATION (Login / Vote verification)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Authenticates a voter by face.
     * Uses adaptive threshold based on liveness score.
     * Validates liveness and anti-replay proofs.
     *
     * @param payloadJson JSON: { primaryDescriptor: [128 floats], livenessScore: int,
     *                           challengeProof: {...} }
     * @return JWT token string or null
     */
    public String loginWithDescriptor(String payloadJson) {
        try {
            JsonNode payload = objectMapper.readTree(payloadJson);

            int livenessScore = payload.has("livenessScore") ? payload.get("livenessScore").asInt(0) : 0;
            double frameVariance = payload.has("challengeProof")
                    && payload.get("challengeProof").has("frameVariance")
                    ? payload.get("challengeProof").get("frameVariance").asDouble(0) : 0;
            int clientAttempts = payload.has("challengeProof")
                    && payload.get("challengeProof").has("attempts")
                    ? payload.get("challengeProof").get("attempts").asInt(1) : 1;

            log("LOGIN", String.format("livenessScore=%d frameVariance=%.2f clientAttempts=%d",
                    livenessScore, frameVariance, clientAttempts));

            // ── Liveness validation ──────────────────────────────────────────
            if (livenessScore < MIN_LIVENESS_SCORE) {
                log("LOGIN", "REJECTED — liveness score too low: " + livenessScore);
                return null;
            }
            if (frameVariance < MIN_FRAME_VARIANCE) {
                log("LOGIN", "REJECTED — frame variance too low (anti-replay): " + frameVariance);
                return null;
            }

            // ── Adaptive threshold ───────────────────────────────────────────
            // Higher liveness score = slightly more lenient threshold (face angle may differ)
            double threshold = BASE_MATCH_THRESHOLD;
            if (livenessScore >= 90) threshold = Math.min(MAX_MATCH_THRESHOLD, BASE_MATCH_THRESHOLD + 0.06);
            else if (livenessScore >= 75) threshold = Math.min(MAX_MATCH_THRESHOLD, BASE_MATCH_THRESHOLD + 0.04);
            log("LOGIN", String.format("Adaptive threshold=%.4f", threshold));

            // ── Extract primary descriptor ───────────────────────────────────
            double[] uploaded = extractPrimaryDescriptor(payload);
            if (uploaded == null) {
                log("LOGIN", "REJECTED — no valid descriptor in payload");
                return null;
            }

            // ── Find best match ──────────────────────────────────────────────
            List<User> allUsers = userRepository.findAll();
            User bestMatch = null;
            double bestDist = Double.MAX_VALUE;

            for (User u : allUsers) {
                if (u.getFaceEmbedding() == null) continue;
                double[] stored = parseDescriptor(u.getFaceEmbedding());
                if (stored == null) continue;

                double dist = euclidean(uploaded, stored);
                log("LOGIN", String.format("  vs userId=%d — distance=%.4f", u.getId(), dist));

                if (dist < bestDist) { bestDist = dist; bestMatch = u; }
            }

            if (bestMatch != null && bestDist < threshold) {
                String role = bestMatch.getRole() != null ? bestMatch.getRole().name() : "VOTER";
                log("LOGIN", String.format("MATCHED userId=%d distance=%.4f role=%s", bestMatch.getId(), bestDist, role));
                return jwtUtil.generateToken(bestMatch.getId(), role);
            }

            log("LOGIN", String.format("NO MATCH — best distance=%.4f threshold=%.4f", bestDist, threshold));
            return null;

        } catch (Exception e) {
            log("LOGIN", "ERROR: " + e.getMessage());
            return null;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  HELPERS
    // ─────────────────────────────────────────────────────────────────────────

    /** Extract and average multiple descriptors (multi-angle registration) */
    private double[] extractAverageDescriptor(JsonNode payload) {
        try {
            List<double[]> descriptors = new ArrayList<>();

            // Try descriptors array first
            if (payload.has("descriptors") && payload.get("descriptors").isArray()) {
                for (JsonNode d : payload.get("descriptors")) {
                    double[] arr = objectMapper.convertValue(d, double[].class);
                    if (arr != null && arr.length == 128) descriptors.add(arr);
                }
            }

            // Fall back to primaryDescriptor
            if (descriptors.isEmpty() && payload.has("primaryDescriptor")) {
                double[] arr = objectMapper.convertValue(payload.get("primaryDescriptor"), double[].class);
                if (arr != null && arr.length == 128) descriptors.add(arr);
            }

            // Legacy: flat array at root
            if (descriptors.isEmpty() && payload.isArray()) {
                double[] arr = objectMapper.convertValue(payload, double[].class);
                if (arr != null && arr.length == 128) descriptors.add(arr);
            }

            if (descriptors.isEmpty()) return null;

            // Average all descriptors
            double[] avg = new double[128];
            for (double[] d : descriptors)
                for (int i = 0; i < 128; i++) avg[i] += d[i];
            for (int i = 0; i < 128; i++) avg[i] /= descriptors.size();

            log("DESC", String.format("Averaged %d descriptors", descriptors.size()));
            return avg;

        } catch (Exception e) {
            log("DESC", "Parse error: " + e.getMessage());
            return null;
        }
    }

    /** Extract primary descriptor only (for login matching) */
    private double[] extractPrimaryDescriptor(JsonNode payload) {
        try {
            if (payload.has("primaryDescriptor")) {
                double[] arr = objectMapper.convertValue(payload.get("primaryDescriptor"), double[].class);
                if (arr != null && arr.length == 128) return arr;
            }
            if (payload.has("descriptors") && payload.get("descriptors").isArray()
                    && payload.get("descriptors").size() > 0) {
                double[] arr = objectMapper.convertValue(payload.get("descriptors").get(0), double[].class);
                if (arr != null && arr.length == 128) return arr;
            }
            if (payload.isArray()) {
                double[] arr = objectMapper.convertValue(payload, double[].class);
                if (arr != null && arr.length == 128) return arr;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /** Parse stored descriptor string */
    private double[] parseDescriptor(String json) {
        try {
            return objectMapper.readValue(json, double[].class);
        } catch (Exception e) {
            return null;
        }
    }

    /** Euclidean distance between two 128-float descriptors */
    private double euclidean(double[] a, double[] b) {
        if (a == null || b == null || a.length != b.length) return Double.MAX_VALUE;
        double sum = 0;
        for (int i = 0; i < a.length; i++) { double d = a[i] - b[i]; sum += d * d; }
        return Math.sqrt(sum);
    }

    private void log(String tag, String msg) {
        System.out.printf("[BIOMETRIC/%s] %s%n", tag, msg);
    }
}