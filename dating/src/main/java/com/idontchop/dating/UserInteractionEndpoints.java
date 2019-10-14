package com.idontchop.dating;


import java.util.List;

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
import entities.Likes;
import entities.User;
import repositories.BlocksRepository;
import repositories.FavoritesRepository;
import repositories.HidesRepository;
import repositories.LikesRepository;
import repositories.UserRepository;
import rest_entities.InteractionsList;
import rest_entities.RestMessage;


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

	// held to avoid unnecessary DB requests
	private User currentUser;
	
	/**
	 * Takes care of finding and saving interactions when type not explicit.
	 */
	@Autowired
	private InteractionsService iService;
	
	/**
	 * This endpoint returns arrays for connections, favorites, and likes. It is used
	 * by the client after a search has been made to update the UI.
	 * 
	 * These interactions are not included in the main search for performance reasons.
	 *  
	 * @param userList The client only requests an interaction list on users it doesn't
	 * already know
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping ( value = "/interactionsList", method = RequestMethod.GET )
	public InteractionsList getInteractionsList ( @RequestParam long[] userList ) {
		InteractionsList interactionsList = new InteractionsList();

		interactionsList.setConnections(
				iService.findConnections( getUser().getId(), userList)
				);
		
		interactionsList.setFavorites(
				(Iterable<Favorites>) iService.findInteractions(getUser().getId(), userList, IType.FAV)
				);
		
		interactionsList.setLikes(
				(Iterable<Likes>) iService.findInteractions(getUser().getId(), userList, IType.LIKE)
				);
		
		return interactionsList;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping ( value = "/myFavorites", method = RequestMethod.GET )
	public Iterable<Favorites> getMyFavorites ( ) {		
		return (Iterable<Favorites>) iService.getInteractions(getUser(), IType.FAV);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping ( value = "/myLikes", method = RequestMethod.GET )
	public Iterable<Likes> getLikes ( ) {		
		return (Iterable<Likes>) iService.getInteractions(getUser(), IType.LIKE);
	}
	
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
	@RequestMapping ( value = "/addFav", method = RequestMethod.POST)
	public RestMessage addFavorite ( @RequestParam (defaultValue = "-1L") Long target
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
	public RestMessage addBlock ( @RequestParam (defaultValue = "-1L") Long target ) {
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
	public RestMessage addHide ( @RequestParam (defaultValue = "-1L") Long target ) {
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
	public RestMessage addLike ( @RequestParam ( defaultValue = "-1L") Long target ) {
		return addInteraction ( IType.LIKE, target );
	}
	
	/**
	 * removes target favorite
	 */
	@RequestMapping ( value = "addFav", method = RequestMethod.DELETE )
	public RestMessage remFav ( @RequestParam ( defaultValue = "-1L") Long target ) {
		return remInteraction ( IType.FAV, target);
	}
	
	/**
	 * remove Like
	 * @param target
	 * @return
	 */
	@RequestMapping ( value = "addLike", method = RequestMethod.DELETE )
	public RestMessage remLike ( @RequestParam ( defaultValue = "-1L") Long target ) {
		return remInteraction ( IType.LIKE, target );
	}
	
	/**
	 * remove Block
	 * @param target
	 * @return
	 */
	@RequestMapping ( value = "addBlock", method = RequestMethod.DELETE )
	public RestMessage remBlock ( @RequestParam ( defaultValue = "-1L") Long target ) {
		return remInteraction ( IType.BLOCK, target );
	}
	
	/**
	 * Remove Hide
	 * @param target
	 * @return
	 */
	@RequestMapping ( value = "addHide", method = RequestMethod.DELETE )
	public RestMessage remHide ( @RequestParam ( defaultValue = "-1L") Long target ) {
		return remInteraction ( IType.HIDE, target );
	}
	
	/**
	 * Adds an Interaction handles return string for add endpoints
	 * 
	 * @param itype enum that symbols the type of interaction
	 * @param target the user target
	 * @return String: "success" when added. Other strings indicate errors
	 */
	private RestMessage addInteraction ( IType itype, Long target ) {
		
		// not very resty
		if ( target == -1 ) return new RestMessage("no target");
		
		User from = getUser();
		
		// strange case but should check
		if ( target == from.getId() ) return new RestMessage("target self");
		
		try {
			
			User to = userRepository.findById(target).get();

			//Favorites newFav = new Favorites( u, to );
			Interactions newInteraction = itype.get( from, to );
			
			iService.write(newInteraction);
			
		} catch ( java.util.NoSuchElementException e ) {
			return new RestMessage(e.getMessage());
		} catch ( Exception e ) {
			return new RestMessage(e.getMessage());
		}
		
		return new RestMessage("added");
	}
	
	/**
	 * API with InteractionsService to remove an Interaction
	 * 
	 * @param itype
	 * @param target the target id (toId), fromId is current user
	 * @return string "success" or an error message
	 */
	private RestMessage remInteraction ( IType itype, Long target ) {
		
		// see addInteraction
		
		if ( target == -1 ) return new RestMessage("No Target");
		
		User from = getUser();
		
		if ( target == from.getId() ) return new RestMessage ("Target Self");
		
		try {
			iService.remove( from.getId(), target, itype);
		} catch ( Exception e ) {
			return new RestMessage (e.getMessage());
		}
		return new RestMessage ("removed");
		
	}
	
	
	/**
	 * Helper function, returns the User entity of the currently logged in user
	 */
	private User getUser () {
		if ( currentUser == null ) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			currentUser = userRepository.findByUserSecurity_Username(currentPrincipalName);
		}
		return currentUser;
	}
	
	
}
