package com.idontchop.dating;

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
