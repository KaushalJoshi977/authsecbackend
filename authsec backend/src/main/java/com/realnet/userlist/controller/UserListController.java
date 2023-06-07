package com.realnet.userlist.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.realnet.userlist.entity.UserImage;
import com.realnet.userlist.entity.UserList;
import com.realnet.userlist.repository.UserImageRepository;
import com.realnet.userlist.service.UserListService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
//@CrossOrigin("*")
@RequestMapping(value = "/user_list", produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"/user_list"})
public class UserListController {
	
	@Autowired
	private UserImageRepository userImageRepository;
	
	@Autowired
	private UserListService userListService;
	
	@PostMapping("/create")
	public ResponseEntity<?> add(@RequestBody UserList user){
		UserList addToDb = this.userListService.addToDb(user);
		return ResponseEntity.ok(addToDb);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserList user){
		UserList updateToDb = this.userListService.updateToDb(user);
		return ResponseEntity.ok(updateToDb);
	}
	
	@GetMapping("/get-one/{id}")
	public ResponseEntity<?> getOne(@PathVariable("id") Long id){
		UserList oneById = this.userListService.getOneById(id);
		return ResponseEntity.ok(oneById);
	}
	
	@GetMapping("/get-all")
	public List<UserList> getAll(){
		List<UserList> allFromDb = this.userListService.getAllFromDb();
		return allFromDb;
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Long id) {
		System.out.println("Request came to delete API..");
		
		// parsing Long value into String and then call get image by user's id API.
		String rawId = String.valueOf(id);
		Optional<UserImage> findByUser = this.userImageRepository.findByUser(rawId);
		
		// delete users image before deleting user.
		this.userImageRepository.deleteById(findByUser.get().getId());
		
		// delete user after deleting profile image.
		this.userListService.deleteFromDbById(id);
	}
	
	@PostMapping("/upload")
	public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file,
									@RequestParam("user_id") Long user_id) throws IOException {

		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		UserImage file1 = new UserImage(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		
		file1.setFilename(file.getOriginalFilename());
		file1.setType(file.getContentType());
		file1.setUser_id(String.valueOf(user_id));
		
		this.userImageRepository.save(file1);
		return ResponseEntity.status(HttpStatus.OK);
	}

	@GetMapping("/get/{user}")
	public UserImage getImage(@PathVariable("user") Integer user_id) throws Exception {
		
		System.out.println("Id of User is: " + user_id);
		String rawId = String.valueOf(user_id);

		Optional<UserImage> retrievedImage = null;
		try {
			retrievedImage = userImageRepository.findByUser(rawId);
		} catch (Exception e) {
			System.out.println("No image found for this user");
		}
		
//		if(retrievedImage == null) {
//			throw new Exception("No image found for this user");
//		}
		UserImage img = new UserImage(retrievedImage.get().getFilename(), retrievedImage.get().getType(),
				decompressBytes(retrievedImage.get().getPicByte()));
		return img;
	}
	
//	@GetMapping("/get/{imageName}")
//	public UserImage getImage(@PathVariable("imageName") String imageName) throws IOException {
//		
//		System.out.println("Name of file is: " + imageName);
//
//		final Optional<UserImage> retrievedImage = userImageRepository.findByFilename(imageName);
//		UserImage img = new UserImage(retrievedImage.get().getFilename(), retrievedImage.get().getType(),
//				decompressBytes(retrievedImage.get().getPicByte()));
//		return img;
//	}
//	
	// compress the image bytes before storing it in the database
			public static byte[] compressBytes(byte[] data) {
				Deflater deflater = new Deflater();
				deflater.setInput(data);
				deflater.finish();

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
				byte[] buffer = new byte[1024];
				while (!deflater.finished()) {
					int count = deflater.deflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				try {
					outputStream.close();
				} catch (IOException e) {
				}
				System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

				return outputStream.toByteArray();
			}

			// uncompress the image bytes before returning it to the angular application
			public static byte[] decompressBytes(byte[] data) {
				Inflater inflater = new Inflater();
				inflater.setInput(data);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
				byte[] buffer = new byte[1024];
				try {
					while (!inflater.finished()) {
						int count = inflater.inflate(buffer);
						outputStream.write(buffer, 0, count);
					}
					outputStream.close();
				} catch (IOException ioe) {
				} catch (DataFormatException e) {
				}
				return outputStream.toByteArray();
			}

	
	
}
