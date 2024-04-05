package AmazonDemo.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = JwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwtToken));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials");
        }

    }

    @PostMapping("/createNewUser")
    public ResponseEntity createNewUser(@RequestBody LoginRequest request) {
        Optional<CustomUser> customUserOptional = customUserRepository.findById(request.getUsername());
        if (customUserOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username already exists");
        }

        CustomUser user = new CustomUser();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        customUserRepository.save(user);
        return ResponseEntity.ok("success");
    }
}
