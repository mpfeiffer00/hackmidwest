package com.hackmidwest.cloudinary;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.Singleton;
import com.cloudinary.SingletonManager;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		Cloudinary cloudinary = new Cloudinary
				(ObjectUtils.asMap("cloud_name", "dpgwnhn5s", "api_key", "716471849818819",
				"api_secret", "bsAvNta4VMZzBVkxoq8mqfkQ8mk"));
		
		SingletonManager manager = new SingletonManager();
		manager.setCloudinary(cloudinary);
		manager.init();
		File toUpload = new File("download.jpg");
		Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
	}
}
