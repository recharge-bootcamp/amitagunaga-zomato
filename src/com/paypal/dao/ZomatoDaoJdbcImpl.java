package com.paypal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.paypal.exception.ApplicationException;
import com.paypal.exception.DaoException;
import com.paypal.model.Parameter;
import com.paypal.model.Ratings;
import com.paypal.model.Restaurant;
import com.paypal.model.Review;
import com.paypal.model.SearchResult;

public class ZomatoDaoJdbcImpl implements ZomatoDao {
	
	private String driverName="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/project";
	private String username="root";
	private String password="SqlRoot3!";

	
	private Connection getConnection() throws ApplicationException {
		Connection conn = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return conn;
	}
	
	
	@Override
	public void addARestaurant(Restaurant restaurant) throws ApplicationException, DaoException  {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "insert into RESTAURANT(name, address) values (?,?)";
		connection = getConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, restaurant.getName());
			pstmt.setString(2, restaurant.getAddress().trim());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		finally {
			closeConnection(connection);
			closeStatement(pstmt);
			closeResultSet(rs);
		}
	}

	public List<Restaurant> getAllRestaurants() throws DaoException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select RESTAURANT_ID,NAME from RESTAURANT";
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Restaurant r = new Restaurant();
				r.setRestaurantId(rs.getInt("RESTAURANT_ID"));
				r.setName(rs.getString("NAME"));
				restaurants.add(r);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return restaurants;
	}
	
	private void closeResultSet(ResultSet rs) throws ApplicationException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
		}
	}


	private void closeStatement(PreparedStatement pstmt) throws ApplicationException {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
		}
	}

	private void closeConnection(Connection connection) throws ApplicationException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException();
			}
		}
	}


	@Override
	public List<Parameter> getAllParameters() throws DaoException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select id,name from parameters";
		List<Parameter> parameters = new ArrayList<Parameter>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Parameter p = new Parameter();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				parameters.add(p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return parameters;
	}


	@Override
	public List<Ratings> getAllRatings() throws DaoException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select id,name from ratings";
		List<Ratings> ratings = new ArrayList<Ratings>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Ratings r = new Ratings();
				r.setId(rs.getInt("id"));
				r.setName(rs.getString("name"));
				ratings.add(r);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return ratings;
	}


	@Override
	public void addReview(Review review) throws ApplicationException, DaoException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "insert into review(name, restaurantName, parameterName, ratingId, reviewDiscription) values(?,?,?,?,?)";
		conn = getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, review.getName());
			pstmt.setString(2, review.getRestaurant());
			pstmt.setString(3, review.getParameter());
			pstmt.setInt(4, review.getRatingId());
			pstmt.setString(5, review.getReviewDiscription().trim());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}


	@Override
	public SearchResult calculateOverallRatingsOfARestaurant(String restaurant, String parameter)
			throws ApplicationException, DaoException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "select restaurantName, parameterName, AVG(ratingId) from review where restaurantName=\""+restaurant+"\" group by parameterName having parameterName=\""+parameter+"\"";
		SearchResult resultObj = new SearchResult();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				resultObj.setRestaurantName(rs.getString(1));
				resultObj.setParameter(rs.getString(2));
				resultObj.setAverageRating(rs.getFloat(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return resultObj;
	}


	@Override
	public List<SearchResult> getFilteredRestaurants(int foodRating, int ambianceRating, int serviceRating) throws DaoException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<SearchResult> resultList = new ArrayList<>();
		String sql = "select restaurantName, parameterName, AVG(ratingId) from review group by parameterName, restaurantName";
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				SearchResult resultObj = new SearchResult();
				resultObj.setRestaurantName(rs.getString(1));
				resultObj.setParameter(rs.getString(2));
				resultObj.setAverageRating(rs.getFloat(3));
				resultList.add(resultObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return resultList;
	}
}
