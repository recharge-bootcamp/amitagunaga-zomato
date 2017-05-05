package com.paypal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.dao.ZomatoDao;
import com.paypal.dao.ZomatoDaoJdbcImpl;
import com.paypal.exception.ApplicationException;
import com.paypal.exception.DaoException;
import com.paypal.model.Parameter;
import com.paypal.model.Ratings;
import com.paypal.model.Restaurant;
import com.paypal.model.Result;
import com.paypal.model.Review;
import com.paypal.model.SearchResult;

/**
 * Servlet implementation class FrontControllerServlet
 */
@WebServlet("/FrontControllerServlet")
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String GET_ADD_RESTAURANT_FORM="addRestaurantForm.action";
	private static final String ADD_RESTAURANT_TO_DB="addRestaurantToDB.action";
	private static final String GET_ADD_REVIEWS_FORM="addReviewsToRestaurantForm.action";
	private static final String ADD_REVIEWS_TO_DB="addReviewsToDB.action";
	private static final String GET_RATINGS_TO_A_RESTAURANT="getRatingsToARestaurantForm.action";
	private static final String GET_RATINGS_OF_A_RESTAURANT="getRatingsFromDB.action";
	private static final String GET_SEARCH_OPTIONS_FORM="searchFormPage.action";
	private static final String FILTER_RESTAURANTS_FROM_DB="filterRestaurantsFromDB.action";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri =  request.getRequestURI();
		if(uri.endsWith(GET_ADD_RESTAURANT_FORM)) {
			getAddRestaurantFormAction(request,response);
		}
		else if(uri.endsWith(ADD_RESTAURANT_TO_DB)) {
			getAddARestaurantToDBAction(request, response);
		}
		else if(uri.endsWith(GET_ADD_REVIEWS_FORM)) {
			getAddReviewsToRestaurantForm(request, response);
		}
		else if(uri.endsWith(ADD_REVIEWS_TO_DB)){
			getAddReviewsToDBAction(request,response);
		}
		else if(uri.endsWith(GET_SEARCH_OPTIONS_FORM)) {
			getSearchOptionForm(request,response);
		}
		else if(uri.endsWith(GET_RATINGS_TO_A_RESTAURANT)) {
			getRatingsForARestaurantForm(request,response);
		}
		else if(uri.endsWith(GET_RATINGS_OF_A_RESTAURANT)){
			getOverallRatingsFromDB(request, response);
		}
		else if(uri.endsWith(FILTER_RESTAURANTS_FROM_DB)){
			filterRestaurantsFromDB(request,response);
		}
	}
	
	private void filterRestaurantsFromDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ZomatoDao dao = new ZomatoDaoJdbcImpl();
		String forwardPath = "showFilteredRestaurant.jsp";
		
		int foodRating = Integer.parseInt(request.getParameter("foodRating"));
		int ambianceRating = Integer.parseInt(request.getParameter("ambianceRating"));
		int serviceRating = Integer.parseInt(request.getParameter("serviceRating"));
		List<SearchResult> results = null;
		
		try{
			results = dao.getFilteredRestaurants(foodRating, ambianceRating, serviceRating);
		}
		catch(DaoException e){
			e.printStackTrace();
			forwardPath = "errorPage.jsp";
			request.setAttribute("errorMess", e.getMessage());
		}
		List<Result> finalResultList = new ArrayList<>();
		for (SearchResult dbResultObj : results) {
			if("food".equalsIgnoreCase(dbResultObj.getParameter())) {
				if(dbResultObj.getAverageRating() >= foodRating) {
					Result resultFood = new Result();
					resultFood.setRestaurantName(dbResultObj.getRestaurantName());
					resultFood.setParameter(dbResultObj.getParameter());
					finalResultList.add(resultFood);
				}
			}
			else if("ambiance".equalsIgnoreCase(dbResultObj.getParameter())){
				if(dbResultObj.getAverageRating() >= ambianceRating){
					Result resultAmbiance = new Result();
					resultAmbiance.setRestaurantName(dbResultObj.getRestaurantName());
					resultAmbiance.setParameter(dbResultObj.getParameter());
					finalResultList.add(resultAmbiance);
				}
			}
			else if("service".equalsIgnoreCase(dbResultObj.getParameter())){
				if(dbResultObj.getAverageRating() >= serviceRating){
					Result resultService = new Result();
					resultService.setRestaurantName(dbResultObj.getRestaurantName());
					resultService.setParameter(dbResultObj.getParameter());
					finalResultList.add(resultService);
				}
			}
		}
		request.setAttribute("finalResultList", finalResultList);
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	private void getOverallRatingsFromDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ZomatoDao dao = new ZomatoDaoJdbcImpl();
		String forwardPath = "successPage.jsp";
		
		String restaurantName = request.getParameter("restaurant");
		String parameter = request.getParameter("parameters");
		SearchResult result = null;
		String message;
		try {
			result = dao.calculateOverallRatingsOfARestaurant(restaurantName, parameter);
		} catch (ApplicationException e) {
			e.printStackTrace();
			forwardPath = "errorPage.jsp";
			request.setAttribute("errorMess", e.getMessage());
		} catch (DaoException e) {
			e.printStackTrace();
			forwardPath = "errorPage.jsp";
			request.setAttribute("errorMess", e.getMessage());
		}
		if(result.getAverageRating() == 0) {
			message = "No ratings available yet for this restaurant "+restaurantName;
		}else{
			message = result.getRestaurantName()+" has an overall rating of "+result.getAverageRating()+" on "+result.getParameter();
		}
		request.setAttribute("successMess", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	private void getRatingsForARestaurantForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPath = "GetRatings.jsp";
		ZomatoDao dao = new ZomatoDaoJdbcImpl();
		try {
			List<Restaurant> restaurants = dao.getAllRestaurants();
			List<Parameter> parameters = dao.getAllParameters();
			request.setAttribute("restaurants", restaurants);
			request.setAttribute("parameters", parameters);
		} catch (DaoException e) {
			e.printStackTrace();
			forwardPath = "errorPage.jsp";
			request.setAttribute("errorMess", e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	private void getAddRestaurantFormAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPath = "addRestaurantForm.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}
	private void getAddARestaurantToDBAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ZomatoDao dao = new ZomatoDaoJdbcImpl();
		String forwardPath = "successPage.jsp";
		
		Restaurant restaurant = new Restaurant();
		restaurant.setName(request.getParameter("name"));
		restaurant.setAddress(request.getParameter("address"));
		
		try {
			dao.addARestaurant(restaurant);
		} catch (ApplicationException e) {
			e.printStackTrace();
			forwardPath = "errorPage.jsp";
			request.setAttribute("errorMess", e.getMessage());
		} catch (DaoException e) {
			e.printStackTrace();
			forwardPath = "errorPage.jsp";
			request.setAttribute("errorMess", e.getMessage());
		}
		request.setAttribute("successMess", "Restaurant "+restaurant.getName()+" successfully added");
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}
	
	private void getAddReviewsToRestaurantForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPath = "ReviewARestaurant.jsp";
		ZomatoDao dao = new ZomatoDaoJdbcImpl();
		try {
			List<Restaurant> restaurants = dao.getAllRestaurants();
			List<Parameter> parameters = dao.getAllParameters();
			List<Ratings> ratings = dao.getAllRatings();
			request.setAttribute("restaurants", restaurants);
			request.setAttribute("parameters", parameters);
			request.setAttribute("ratings", ratings);
		} catch (DaoException e) {
			e.printStackTrace();
			forwardPath = "errorPage.jsp";
			request.setAttribute("errorMess", e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}
	
	private void getAddReviewsToDBAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPath = "successPage.jsp";
		ZomatoDao dao = new ZomatoDaoJdbcImpl();
		
		try {
			Review r = new Review();
			r.setName(request.getParameter("name"));
			r.setRestaurant(request.getParameter("restaurant"));
			r.setParameter(request.getParameter("parameters"));
			r.setRatingId(Integer.parseInt(request.getParameter("ratings")));
			r.setReviewDiscription(request.getParameter("review"));
			
			dao.addReview(r);
			request.setAttribute("successMess", "Review successfully added to "+r.getRestaurant()+" by "+r.getName()+"....... Thank you!!");
		} catch (ApplicationException e) {
			e.printStackTrace();
			request.setAttribute("errorMess",e.getMessage());
			forwardPath="errorPage.jsp";
		} catch (DaoException e) {
			e.printStackTrace();
			request.setAttribute("errorMess",e.getMessage());
			forwardPath="errorPage.jsp";
		}
		RequestDispatcher dis=request.getRequestDispatcher(forwardPath);
		dis.forward(request, response);
	}

	private void getSearchOptionForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPath="searchForm.jsp";
		ZomatoDao dao = new ZomatoDaoJdbcImpl();
		try {
			List<Ratings> ratings = dao.getAllRatings();
			request.setAttribute("ratings", ratings);
		} catch (DaoException e) {
			e.printStackTrace();
			forwardPath = "errorPage.jsp";
			request.setAttribute("errorMess", e.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}
}
