package merliontechs.repository;

import merliontechs.domain.Product;

import merliontechs.repository.projections.ProductBestSelling;
import merliontechs.repository.projections.ProductMostProfitable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(
        value = "SELECT p.id AS id, p.name AS name, COUNT(p.name) AS sales " +
                "FROM Product p " +
                    "JOIN Sales s " +
                        "ON p.id = s.product_id " +
                "GROUP BY p.id, p.name " +
                "ORDER BY 3 DESC " +
                "FETCH FIRST 5 ROWS ONLY" // FETCH FIRST ... es ANSI SQL
        , nativeQuery = true
    )
    List<ProductBestSelling> findFiveBestSellingProducts();

    @Query(
        value = "SELECT p.id AS id, p.name AS name, SUM(p.price) AS profits " +
                "FROM Product p " +
                    "JOIN Sales s " +
                        "ON p.id = s.product_id " +
                "GROUP BY p.id, p.name " +
                "ORDER BY 3 DESC " +
                "FETCH FIRST 5 ROWS ONLY"
        , nativeQuery = true
    )
    List<ProductMostProfitable> findFiveMostRevenueProducts();
}
