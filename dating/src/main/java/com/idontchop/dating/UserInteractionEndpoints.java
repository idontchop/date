package com.idontchop.dating;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import entities.Blocks;
import entities.Favorites;
import entities.Interactions;
import entities.User;
import repositories.BlocksRepository;
import repositories.FavoritesRepository;
import repositories.HidesRepository;
import repositories.LikesRepository;
import repositories.UserRepository;


/**
 * This controller handles the interactions between users controlled by the junction
 * tables: block, favorite, like, Hide, Gift
 * 
 * They work the same except Gift which has configurable options.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@RestController

public class UserInteractionEndpoints {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Takes care of finding and saving interactions when type not explicit.
	 */
	@Autowired
	private InteractionsService iService;
	
	/**
	 * This adds a favorite for the current user. Favorites are a oneway
	 * linking tool by the user to keep track of certain users. This tool
	 * will likely be linked to by a drop down. It is not the same as liking
	 * which is two-way. 
	 * 
	 * @param target the id of user to link to as favorite
	 * @param principal the current user's security information
	 * @return
	 */
	@RequestMapping ( value = "/addFavorite", method = RequestMethod.POST)
	public String addFavorite ( @RequestParam (defaultValue = "-1L") Long target
								 ) {
		return addInteraction ( IType.FAV, target );		
	}
	
	/**
	 * Adds a block for the current user. Blocks are checked on all searches and excludes
	 * the pair. Works two-way.
	 * @param target The blocked user
	 * @return see addInteraction
	 */
	@RequestMapping ( value = "/addBlock", method = RequestMethod.POST )
	public String addBlock ( @RequestParam (defaultValue = "-1L") Long target ) {
		return addInteraction ( IType.BLOCK, target );
	}
	
	/**
	 * Adds a Hide for the current user. Hides are used so an unliked user is not
	 * shown in a search, but hides do not block interactions between the users.
	 * Hides can be toggled when performing a new search.
	 * @param target The hidden user
	 * @return see addInteraction
	 */
	@RequestMapping ( value = "/addHide", method = RequestMethod.POST )
	public String addHide ( @RequestParam (defaultValue = "-1L") Long target ) {
		return addInteraction ( IType.HIDE, target );
	}
	
	/**
	 * Adds a Like for the current user. Likes are used when the user wants to connect
	 * with the user but does not wish to do a more noticable interaction. Likes must be
	 * mutual for free messaging
	 * @param target The liked user
	 * @return see addInteraction
	 */
	@RequestMapping ( value = "addLike", method = RequestMethod.POST )
	public String addLike ( @RequestParam ( defaultValue = "-1L") Long target ) {
		return addInteraction ( IType.LIKE, target );
	}
	

	/**
	 * Adds an Interaction handles return string for add endpoints
	 * 
	 * @param itype enum that symbols the type of interaction
	 * @param target the user target
	 * @return String: "success" when added. Other strings indicate errors
	 */
	private String addInteraction ( IType itype, Long target ) {
		
		// not very resty
		if ( target == -1 ) return "no target";
		
		User from = getUser();
		
		// strange case but should check
		if ( target == from.getId() ) return "target self";
		
		try {
			
			User to = userRepository.findById(target).get();

			//Favorites newFav = new Favorites( u, to );
			Interactions newInteraction = itype.get( from, to );
			
			iService.write(newInteraction);
			
		} catch ( java.util.NoSuchElementException e ) {
			return e.getMessage();
		} catch ( Exception e ) {
			return e.getMessage();
		}
		
		return "success";
	}
	
	
	/**
	 * Helper function, returns the User entity of the currently logged in user
	 */
	private User getUser () {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User u = userRepository.findByUserSecurity_Username(currentPrincipalName);
		return u;
	}
}
