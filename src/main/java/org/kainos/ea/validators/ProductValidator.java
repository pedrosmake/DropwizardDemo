package org.kainos.ea.validators;

import org.kainos.ea.exceptions.Entity;
import org.kainos.ea.exceptions.InvalidException;
import org.kainos.ea.models.ProductRequest;

public class ProductValidator {
	public void validateProduct(ProductRequest productRequest) throws InvalidException {
		if (productRequest.getName().length() > 50) {
			throw new InvalidException(Entity.PRODUCT, "Name greater than 50 characters");
		}

		if (productRequest.getDescription().length() > 500) {
			throw new InvalidException(Entity.PRODUCT, "Description greater than 500 characters");
		}

		if (productRequest.getPrice() < 10) {
			throw new InvalidException(Entity.PRODUCT, "Price less than 10 GBP");
		}
	}
}
