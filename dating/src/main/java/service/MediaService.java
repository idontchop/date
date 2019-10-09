package service;

import java.io.IOException;

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
	 * Likely this is only good for development. Possibly for signup default profile
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
}
