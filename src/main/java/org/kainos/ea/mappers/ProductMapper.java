package org.kainos.ea.mappers;

import org.kainos.ea.models.Product;
import org.kainos.ea.models.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static List<ProductResponse> mapProductListToProductResponseList(
            List<Product> products) {
        return products.stream()
                .map(product -> new ProductResponse(product.getProductId(),
                        product.getProductName(),
                        product.getProductPrice()))
                .collect(Collectors.toList());
    }
}
