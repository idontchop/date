package service;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.dao.*;

import entities.Media;
import entities.User;
import repositories.MediaRepository;

@Service
public class MediaService {
	
	@Autowired
	private MediaRepository mRepository;

	/**
	 * Likely this is only good for development. Possibly for signup default profile.
	 * 
	 * Mainly redundant to storeProfileImage, but uses defaults
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public void storeDefaultImage ( MultipartFile file, User user ) throws IOException {
		
		// take out "path://" etc
		String fileName = StringUtils.cleanPath( file.getOriginalFilename() );
		
		// .. won't work on linux
		if ( fileName.contains("..")) 
			throw new IOException(".. detected in filename, must rename.");
		
		Media newMedia = new Media( file.getBytes() );
		newMedia.setUser(user);
		
		try {
			mRepository.save(newMedia);
		} catch ( DataAccessException e ) {
			throw new IOException ( "Caught DB Error: " + e.getMessage() );
		}

	}
	
	/**
	 * Deletes an image from the user's list, returns false if image
	 * does not exist or if user doesn't have access.
	 * @param id
	 * @param user
	 * @return
	 */
	public boolean deleteProfileImage ( long id, User user ) {
		
		Media media;
		try {
			media = mRepository.findById(id).get();
		} catch (NoSuchElementException e ) {
			return false;
		}
		
		if ( media.getUser().getId() != user.getId() ) return false;
		
		mRepository.delete(media);
		
		return true;
		
		
	}
	
	/**
	 * Stores image into a user's Media Data.
	 * 
	 * @param file
	 * @param priority
	 * @param user
	 * @throws IOException
	 */
	public void storeProfileImage ( MultipartFile file, int priority, User user ) throws IOException {
		
		// take out "path://" etc
		String fileName = StringUtils.cleanPath( file.getOriginalFilename() );
		
		// .. won't work on linux
		if ( fileName.contains("..")) 
			throw new IOException(".. detected in filename, must rename.");
		
		Media newMedia = new Media( file.getBytes() );
		newMedia.setUser(user);
		newMedia.setPriority(priority);
		
		try {
			mRepository.save(newMedia);
		} catch ( DataAccessException e ) {
			throw new IOException ( "Caught DB Error: " + e.getMessage());
		}
		
	}
}
