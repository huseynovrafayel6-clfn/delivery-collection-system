package com.webperside.deliverycollectionsystem;

import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static com.webperside.deliverycollectionsystem.model.enums.user.UserStatus.ACTIVE;

@SpringBootApplication
@RequiredArgsConstructor
public class DeliveryCollectionSystemApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(DeliveryCollectionSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		User user = User.builder()
//
//				.name("rafa")
//				.surname("ello")
//				.status(ACTIVE.name())
//				.email("rafaello@cibobombes.az")
//				.password(encoder.encode("123456"))
//				.phoneNumber("1234567890")
//				.build();
//
//		userRepository.save(user);

//        System.out.println(securityProperties);

//        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
//        keyGenerator.initialize(2048);
//        KeyPair kp = keyGenerator.genKeyPair();
//        PublicKey publicKey = kp.getPublic();
//        PrivateKey privateKey = kp.getPrivate();
//
//        String encoudedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
//        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
//
//        System.out.println(convertToPublicKey(encoudedPublicKey));
//
//        System.out.println();
//
//        System.out.println(convertToPrivateKey(encodedPrivateKey));
	}

	private static String convertToPrivateKey(String key){
		StringBuilder result = new StringBuilder();
		result.append("-----BEGIN PRIVATE KEY-----\n");
		result.append(key);
		result.append("\n-----END PRIVATE KEY-----");
		return result.toString();
	}

	private static String convertToPublicKey(String key){
		StringBuilder result = new StringBuilder();
		result.append("-----BEGIN PUBLIC KEY-----\n");
		result.append(key);
		result.append("\n-----END PUBLIC KEY-----");
		return result.toString();
	}
}
