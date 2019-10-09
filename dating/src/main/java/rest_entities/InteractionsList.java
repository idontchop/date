package rest_entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import entities.Favorites;
import entities.Likes;

/**
 * Used for communicating the Iterable of Interactions between the current user and a Iterable
 * of selected users.
 * 
 * The function here is returning this list to client after it has performed a search so that it
 * can update the highlighted buttons. Since it already has the info of the users on the list
 * it only needs a list of the ids it should highlight
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
public class InteractionsList {

	@JsonIgnore
	private Iterable<Likes> connectionsIterable;
	@JsonIgnore
	private Iterable<Favorites> favoritesIterable;
	@JsonIgnore
	private Iterable<Likes> likesIterable;
	
	private List<Long> connections;
	private List<Long> favorites;
	private List<Long> likes;
	

	public void setConnections(Iterable<Likes> connections) {
		this.connectionsIterable = connections;
		this.connections = new ArrayList<>();
		connections.forEach( e -> this.connections.add(e.getTo().getId()));
	}
	
	public void setFavorites(Iterable<Favorites> favorites) {
		this.favoritesIterable = favorites;
		this.favorites = new ArrayList<>();
		favorites.forEach( e -> this.favorites.add(e.getTo().getId()));
	}

	public void setLikes(Iterable<Likes> likes) {
		this.likesIterable = likes;
		this.likes = new ArrayList<>();
		likes.forEach( e -> this.likes.add(e.getTo().getId()));
	}

	public List<Long> getConnections() {
		return connections;
	}

	public List<Long> getFavorites() {
		return favorites;
	}

	public List<Long> getLikes() {
		return likes;
	}
	
	

	
}
