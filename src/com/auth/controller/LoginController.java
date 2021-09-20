package com.auth.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth.model.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;

@Controller
public class LoginController {

	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("CLIENT_ID", System.getenv("CLIENT_ID"));
		return "login.jsp";
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/validate", produces = "application/json")
	@ResponseBody
	public String validate(@RequestBody String model) {
		Gson gson = new Gson();
		Map<String, String> jsonData = gson.fromJson(model, Map.class);
		if (jsonData.containsKey("idToken")) {
			User user = verifier(jsonData.get("idToken"));
			if (user != null)
				return gson.toJson(user);
		}
		return gson.toJson(null);
	}

	public User verifier(String idToken) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
				.setAudience(Collections.singletonList(System.getenv("CLIENT_ID"))).build();

		try {
			GoogleIdToken token = verifier.verify(idToken);
			if (token != null) {
				Payload payload = token.getPayload();
				System.out.println("Payload:: " + payload);

				String audience = payload.getAudienceAsList().get(0);
				if (audience.equalsIgnoreCase(System.getenv("CLIENT_ID"))) {
					String name = (String) payload.get("name");
					String email = payload.getEmail();
					Long expTime = payload.getExpirationTimeSeconds();

					return new User(name, email, expTime);
				}

			}
		} catch (Exception e) {
			System.err.println("Failed to validate token: " + e.toString());
		}
		return null;
	}
}
