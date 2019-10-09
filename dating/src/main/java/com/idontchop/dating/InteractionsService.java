package com.idontchop.dating;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import entities.Blocks;
import entities.Favorites;
import entities.Hides;
import entities.Interactions;
import entities.InteractionsId;
import entities.Likes;
import entities.User;
import repositories.BlocksRepository;
import repositories.FavoritesRepository;
import repositories.HidesRepository;
import repositories.LikesRepository;

/**
 * This class encapulates logic for writing an interaction to the database
 * 
 * Not sure if this is a great pattern, but since UserInteractionEndpoint will be
 * doing a lot of switching based on the type of Interaction, this seemed a good DRY
 * method.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
@Service
public class InteractionsService {
	@Autowired FavoritesRepository fRepository;
	@Autowired BlocksRepository bRepository;
	@Autowired HidesRepository hRepository;
	@Autowired LikesRepository lRepository;
	
	InteractionsService() {
		
	}

	/**
	 * Finds connections (when two users like each other)
	 * 
	 * @param userList
	 * @return Favorites array
	 */
	public Iterable<Likes> findConnections ( long from, long[] userList ) {
		
		List <InteractionsId> userIds = new ArrayList<>();

		for ( long to : userList ) {
			userIds.add( new InteractionsId( to, from) );			
		}
		
		// here we have who the likes the user
		Iterable<Likes> fromToLikes = lRepository.findAllById(userIds);
		
		// flip them and find again and we have connections
		userIds.clear();
		fromToLikes.forEach( l -> {
			userIds.add( new InteractionsId ( l.getTo().getId(), l.getFrom().getId() ) );
		});
		
		// TODO: need logic here to only return two sided matches
		return lRepository.findAllById(userIds);
	}
	
	/**
	 * Finds all interactions of specified IType.
	 * @param itype
	 * @param userList
	 * @return
	 */
	public Iterable<? extends Interactions> findInteractions ( long from, long[] userList, IType itype ) {
		
		List <InteractionsId> userIds = new ArrayList<>();
		
		// create composite keys
		for ( long to : userList ) {
			userIds.add( new InteractionsId ( from, to ) );
		}
		
		// TODO: gotta be a way to return the right repo from an enum
		if ( itype == IType.FAV )
			return fRepository.findAllById(userIds);
		else if ( itype == IType.BLOCK )
			return bRepository.findAllById(userIds);
		else if ( itype == IType.HIDE )
			return hRepository.findAllById(userIds);
		else return lRepository.findAllById(userIds);
	}
	
	/**
	 * This gets the user's Interactions as a list.
	 * 
	 * Since this could possibly be a long list, may need a paging repo?
	 * @param user the user
	 * @param itype
	 * @return
	 */
	public Iterable <? extends Interactions> getInteractions ( User user, IType itype ) {
		
		if ( itype == IType.FAV )
			return fRepository.findAllByFrom(user);
		else if ( itype == IType.BLOCK )
			return bRepository.findAllByFrom(user);
		else if ( itype == IType.HIDE )
			return hRepository.findAllByFrom(user);
		else if ( itype == IType.LIKE ) 
			return lRepository.findAllByFrom(user);
		else throw new IllegalArgumentException ( itype.getName() + " not implemented in InteractionsService.getInteractions");
	}
	
	/**
	 * Receives an interaction and writes it to the proper repository
	 * @param iAction
	 */
	public void write ( Interactions iAction ) {
		
		if ( iAction instanceof Favorites ) 
			fRepository.save((Favorites) iAction);
		else if ( iAction instanceof Blocks ) 
			bRepository.save((Blocks) iAction);
		else if ( iAction instanceof Hides )
			hRepository.save((Hides) iAction);
		else if ( iAction instanceof Likes )
			lRepository.save((Likes) iAction);
	}
	
	/**
	 * Retrieves Interaction of proper from-to pair ID and type
	 * 
	 * @param from The current user
	 * @param to The target user
	 * @param itype defines the type of Interaciton
	 * @return Interaction of proper type
	 */
	public Interactions get ( long from, long to, IType itype ) throws NoSuchElementException {
		
		// composite key
		InteractionsId id = new InteractionsId(from, to);
		
		if ( itype == IType.FAV )
			return fRepository.findById(id).get();
		else if ( itype == IType.BLOCK )
			return bRepository.findById(id).get();
		else if ( itype == IType.HIDE )
			return hRepository.findById(id).get();
		else if ( itype == IType.LIKE )
			return lRepository.findById(id).get();
		else throw new NoSuchElementException ( "Received improper itype: " + itype.getName());
	}
	
	public void remove ( long from, long to, IType itype ) throws NoSuchElementException {
		
		// composite key
		InteractionsId id = new InteractionsId ( from, to );
		if ( itype == IType.FAV )
			fRepository.deleteById(id);
		else if ( itype == IType.BLOCK )
			bRepository.deleteById(id);
		else if ( itype == IType.HIDE )
			 hRepository.deleteById(id);
		else if ( itype == IType.LIKE )
			lRepository.deleteById(id);
		else throw new NoSuchElementException ( "Received improper itype: " + itype.getName());

	}
	
}
