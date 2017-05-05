package com.paypal.model;

import java.io.Serializable;

public class Review implements Serializable {
	private int id;
	private String name;
	private String restaurant;
	private String parameter;
	private int ratingId;
	private String reviewDiscription;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public int getRatingId() {
		return ratingId;
	}
	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}
	public String getReviewDiscription() {
		return reviewDiscription;
	}
	public void setReviewDiscription(String reviewDiscription) {
		this.reviewDiscription = reviewDiscription;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ratingId;
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
		result = prime * result + ((reviewDiscription == null) ? 0 : reviewDiscription.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (ratingId != other.ratingId)
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		if (reviewDiscription == null) {
			if (other.reviewDiscription != null)
				return false;
		} else if (!reviewDiscription.equals(other.reviewDiscription))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Review [id=" + id + ", name=" + name + ", restaurant=" + restaurant + ", parameter=" + parameter
				+ ", ratingId=" + ratingId + ", reviewDiscription=" + reviewDiscription + "]";
	}
}
