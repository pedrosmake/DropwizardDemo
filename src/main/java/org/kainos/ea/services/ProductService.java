package org.kainos.ea.services;

import org.kainos.ea.daos.OrderDao;
import org.kainos.ea.daos.ProductDao;
import org.kainos.ea.exceptions.DoesNotExistException;
import org.kainos.ea.exceptions.Entity;
import org.kainos.ea.exceptions.FailedToCreateException;
import org.kainos.ea.exceptions.InvalidException;
import org.kainos.ea.mappers.OrderMapper;
import org.kainos.ea.mappers.ProductMapper;
import org.kainos.ea.models.*;
import org.kainos.ea.validators.ProductValidator;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    ProductDao productDao;
    ProductValidator productValidator;

    public ProductService(ProductDao productDao, ProductValidator productValidator) {
        this.productDao = productDao;
        this.productValidator = productValidator;
    }

    public List<ProductResponse> getAllOrders() throws SQLException {
        return ProductMapper.mapProductListToProductResponseList(productDao.getAllOrders());
    }

    public Product getProductById(int productId) throws SQLException, DoesNotExistException {
        Product product = productDao.getProductById(productId);
        if (product == null) {
            throw new DoesNotExistException(Entity.PRODUCT);
        }
        return product;
    }

    public int createProduct(ProductRequest productRequest) throws FailedToCreateException, SQLException, InvalidException {

        productValidator.validateProduct(productRequest);
        int id = productDao.createProduct(productRequest);

        if (id == -1) {
            throw new FailedToCreateException(Entity.PRODUCT);
        }

        return id;
    }

    public void updateProduct(int id, ProductRequest productRequest) throws InvalidException, DoesNotExistException, SQLException {
        productValidator.validateProduct(productRequest);

        Product productToUpdate = productDao.getProductById(id);

        if (productToUpdate == null) {
            throw new DoesNotExistException(Entity.PRODUCT);
        }
        productDao.updateProduct(id, productRequest);
    }

    public void deleteProduct(int id) throws DoesNotExistException, SQLException {
        Product product = productDao.getProductById(id);
        if (product == null) {
            throw new DoesNotExistException(Entity.PRODUCT);
        }
        productDao.deleteProduct(id);
    }

}
