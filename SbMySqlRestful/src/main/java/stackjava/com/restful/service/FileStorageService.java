package stackjava.com.restful.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import stackjava.com.resful.exception.MyFileNotFoundException;

import stackjava.com.resful.exception.FileStorageException;
import stackjava.com.resfull.config.FileStorageProperties;

@Service
public class FileStorageService {
	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) throws FileStorageException {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}
	public String storeFile(MultipartFile file) throws FileStorageException {
		// Normalize file name
		//test
	    String generatedString = this.getAlphaNumericString(10);
		String fileName = generatedString+StringUtils.cleanPath(file.getOriginalFilename());
		try {
            // Check if the file's name contains invalid characters

			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
            // Copy file to the target location (Replacing existing file with the same name)

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException e) {
			// TODO: handle exception
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);

		}
	}
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath=this.fileStorageLocation.resolve(fileName);
			Resource resource=new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			}else {
                throw new MyFileNotFoundException("File not found " + fileName);

			}
		} catch (MalformedURLException e) {
			// TODO: handle exception
            throw new MyFileNotFoundException("File not found " + fileName, e);

		}
	}
	
	 static String getAlphaNumericString(int n) 
	    { 
	  
	        // chose a Character random from this String 
	        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	                                    + "0123456789"
	                                    + "abcdefghijklmnopqrstuvxyz"; 
	  
	        // create StringBuffer size of AlphaNumericString 
	        StringBuilder sb = new StringBuilder(n); 
	  
	        for (int i = 0; i < n; i++) { 
	  
	            // generate a random number between 
	            // 0 to AlphaNumericString variable length 
	            int index 
	                = (int)(AlphaNumericString.length() 
	                        * Math.random()); 
	  
	            // add Character one by one in end of sb 
	            sb.append(AlphaNumericString 
	                          .charAt(index)); 
	        } 
	  
	        return sb.toString(); 
	    } 
}
