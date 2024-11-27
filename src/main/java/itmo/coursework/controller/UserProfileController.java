package itmo.coursework.controller;

import itmo.coursework.model.entity.Profile;
import itmo.coursework.model.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserProfileController {
	@Autowired
	private ProfileRepository profileRepository;
	
	@PostMapping("/users")
	public void saveUsers(@RequestBody Map<String, Object> usersData) {
		List<Map<String, Object>> users = (List<Map<String, Object>>) usersData.get("users");
		for (Map<String, Object> user : users) {
			Profile profile = new Profile();
			profile.setEmail((String) user.get("email"));
			profile.setName((String) user.get("name"));
			profileRepository.save(profile);
		}
	}
	
	@GetMapping("/user/{email}")
	public Profile getUser(@PathVariable String email) {
		return profileRepository.findByEmail(email);
	}
}
