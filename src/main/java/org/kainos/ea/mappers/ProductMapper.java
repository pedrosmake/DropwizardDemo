package org.kainos.ea.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.kainos.ea.models.Product;
import org.kainos.ea.models.ProductResponse;

public class ProductMapper {
    public static List<ProductResponse> mapProductListToProductResponseList (List<Product> products) {
        return products
                .stream()
                .map(product -> new ProductResponse(product.getProductId(), product.getProductName(), product.getProductPrice()))
                .collect(Collectors.toList());
    }
}
