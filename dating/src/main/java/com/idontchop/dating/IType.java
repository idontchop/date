package com.idontchop.dating;

import java.util.stream.Stream;

import entities.Blocks;
import entities.Favorites;
import entities.Hides;
import entities.Interactions;
import entities.Likes;
import entities.User;



/**
 * This enum switches between the types of Interactions. Since each interaction is similar
 * in it's function and often the endpoints will need to retrieve and save data to the database
 * based on the type of Interaction, encapsulating here seemed a good idea.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
public enum IType {


		BLOCK ("Block") {
			@Override
			public Interactions get() {
				return new Blocks();
			}

			@Override
			public Interactions get(User from, User to) {
				return new Blocks (from, to );
			}

		},
		FAV ("Favorite") {
			@Override
			public Interactions get() {
				return new Favorites();
			}


			@Override
			public Interactions get(User from, User to) {
				return new Favorites ( from, to );
			}
		},
		LIKE ("Like") {
			@Override
			public Interactions get() {
				return null;
			}

			@Override
			public Interactions get(User from, User to) {
				return new Likes ( from, to );
			}

		},
		HIDE ("Hide") {
			@Override
			public Interactions get() {
				return null;
			}

			@Override
			public Interactions get(User from, User to) {
				return new Hides ( from, to );
			}

		},
		GIFT ("Gift") {
			@Override
			public Interactions get() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Interactions get(User from, User to) {
				// TODO Auto-generated method stub
				return null;
			}

		};
	
	private String name;
	IType ( String name ) {
		this.name = name;
	}
	
	public String getName () {
		return name;
	}
		

	/**
	 * returns a new Interaction
	 * @return type of interaction empty
	 */
	public abstract Interactions get();
	/**
	 * Returns a new Interaction with Users set
	 * @param from from User (logged in user)
	 * @param to target of Interaction
	 * @return new Interaction
	 */
	public abstract Interactions get( User from, User to );
	/**
	 * essentially corresponds to the repository.save method
	 * TODO: Is this possible? Seperate calls to InteractionsService for now I guess
	 * @param newInteraction
	 */
	//public abstract void save(Interactions newInteraction);
	

	// Stream
	public static Stream<IType> stream() {
		return Stream.of(IType.values());
	}
}
