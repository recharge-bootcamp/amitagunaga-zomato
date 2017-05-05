package com.paypal.dao;

import java.util.List;
import com.paypal.exception.ApplicationException;
import com.paypal.exception.DaoException;
import com.paypal.model.Parameter;
import com.paypal.model.Ratings;
import com.paypal.model.Restaurant;
import com.paypal.model.Review;
import com.paypal.model.SearchResult;

public interface ZomatoDao {

	void addARestaurant(Restaurant restaurant) throws ApplicationException, DaoException;
	
	List<Restaurant> getAllRestaurants() throws DaoException;
	
	List<Parameter> getAllParameters() throws DaoException;
	
	List<Ratings> getAllRatings() throws DaoException;
	
	void addReview(Review review) throws ApplicationException, DaoException;
	
	SearchResult calculateOverallRatingsOfARestaurant(String restaurant, String parameter) throws ApplicationException, DaoException;
	
	List<SearchResult> getFilteredRestaurants(int foodRating, int ambianceRating, int serviceRating) throws DaoException;

}
