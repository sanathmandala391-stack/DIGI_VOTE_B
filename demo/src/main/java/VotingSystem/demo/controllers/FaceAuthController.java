//package VotingSystem.demo.controllers;
//
//import VotingSystem.demo.Service.FaceAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/face")
//public class FaceAuthController {
//
//    @Autowired
//    private FaceAuthService faceAuthService;
//
//    // Register face — receives { userId, descriptor: [128 floats] }
//    @PostMapping("/register")
//    public ResponseEntity<?> registerFace(@RequestBody Map<String, Object> body) {
//        try {
//            Long userId = Long.valueOf(body.get("userId").toString());
//            String descriptorJson = body.get("descriptor").toString();
//
//            boolean success = faceAuthService.registerFaceDescriptor(userId, descriptorJson);
//            if (success) {
//                return ResponseEntity.ok("Face registered successfully");
//            } else {
//                return ResponseEntity.badRequest().body("Failed to register face");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
//        }
//    }
//
//    // Face login — receives { descriptor: [128 floats] }
//    @PostMapping("/login")
//    public ResponseEntity<?> loginWithFace(@RequestBody Map<String, Object> body) {
//        try {
//            String descriptorJson = body.get("descriptor").toString();
//            String token = faceAuthService.loginWithDescriptor(descriptorJson);
//
//            if (token != null) {
//                return ResponseEntity.ok(Map.of("token", token));
//            } else {
//                return ResponseEntity.status(401).body("Face not recognized");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
//        }
//    }
//}



//new Second//

//
//package VotingSystem.demo.controllers;
//
//import VotingSystem.demo.Service.FaceAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/face")
//public class FaceAuthController {
//
//    @Autowired
//    private FaceAuthService faceAuthService;
//
//    @PostMapping("/register")
//    public ResponseEntity<?> registerFace(@RequestBody Map<String, Object> body) {
//        try {
//            Long userId = Long.valueOf(body.get("userId").toString());
//            String descriptorJson = body.get("descriptor").toString();
//
//            boolean success = faceAuthService.registerFaceDescriptor(userId, descriptorJson);
//            if (success) {
//                return ResponseEntity.ok("Face registered successfully");
//            } else {
//                // Could be duplicate face or other error
//                return ResponseEntity.badRequest().body(
//                        "Face registration failed. This face may already be registered with another account. " +
//                                "Each voter must have a unique face. Contact helpdesk@eci.gov.in for assistance."
//                );
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginWithFace(@RequestBody Map<String, Object> body) {
//        try {
//            String descriptorJson = body.get("descriptor").toString();
//            String token = faceAuthService.loginWithDescriptor(descriptorJson);
//
//            if (token != null) {
//                return ResponseEntity.ok(Map.of("token", token));
//            } else {
//                return ResponseEntity.status(401).body("Face not recognized. Please register your face first or use password login.");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
//        }
//    }
//}
//


//Final Touch//

package VotingSystem.demo.controllers;

import VotingSystem.demo.Service.FaceAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Face Authentication Controller
 * Handles /face/register and /face/login endpoints.
 *
 * Accepts advanced payload from frontend:
 * {
 *   "userId": 1,                        // registration only
 *   "descriptors": [[...128 floats...], [...]],
 *   "primaryDescriptor": [...128 floats...],
 *   "livenessScore": 85,
 *   "challengeProof": {
 *     "challengeType": "blink",
 *     "frameVariance": 8.4,
 *     "attempts": 1,
 *     "livenessScore": 85
 *   }
 * }
 */
@RestController
@RequestMapping("/face")
public class FaceAuthController {

    @Autowired
    private FaceAuthService faceAuthService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/register")
    public ResponseEntity<?> registerFace(@RequestBody Map<String, Object> body) {
        try {
            Long userId = Long.valueOf(body.get("userId").toString());
            // Pass full body as JSON to service (it handles multi-descriptor parsing)
            String payloadJson = objectMapper.writeValueAsString(body);

            String result = faceAuthService.registerFaceDescriptor(userId, payloadJson);

            return switch (result) {
                case "ok" -> ResponseEntity.ok("Face registered successfully. Biometric data saved securely.");
                case "duplicate" -> ResponseEntity.status(409).body(
                        "This face is already registered with another account. " +
                                "Each voter must have a unique biometric identity. " +
                                "Contact helpdesk@eci.gov.in if you believe this is an error.");
                case "liveness_fail" -> ResponseEntity.status(422).body(
                        "Liveness verification failed. The system detected a possible spoofing attempt. " +
                                "Please ensure you are performing the challenge live in front of the camera.");
                default -> ResponseEntity.status(500).body(
                        "Face registration could not be completed. Please try again with good lighting.");
            };

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginWithFace(@RequestBody Map<String, Object> body) {
        try {
            String payloadJson = objectMapper.writeValueAsString(body);
            String token = faceAuthService.loginWithDescriptor(payloadJson);

            if (token != null) {
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                return ResponseEntity.status(401).body(
                        "Biometric authentication failed. Face not recognised or liveness check not passed. " +
                                "Please ensure good lighting and follow the challenge instruction. " +
                                "You may also use password login.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        }
    }
}
