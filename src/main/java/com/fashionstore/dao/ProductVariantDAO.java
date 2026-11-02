package com.fashionstore.dao;

import java.math.BigDecimal;
import java.util.List;
import com.fashionstore.model.ProductVariant;

public interface ProductVariantDAO {

    boolean addProductVariant(ProductVariant productVariant);

    boolean updateProductVariant(ProductVariant productVariant);

    boolean deleteProductVariant(int variantId);

    ProductVariant getVariantById(int variantId);

    ProductVariant getVariantByProductIdAndSize(int productId, String size);

    List<ProductVariant> getVariantsByProductId(int productId);

    List<ProductVariant> getAllVariants();

    boolean updateStock(int variantId, int stockQuantity);

    boolean isStockAvailable(int variantId, int requiredQuantity);

	List<ProductVariant> getFilteredProducts(Integer categoryId, String keyword, BigDecimal minPrice);

	List<ProductVariant> getRelatedProducts(int categoryId, int excludeProductId, int limit);
}
