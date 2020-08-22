package merliontechs.repository;

import merliontechs.domain.Sales;

import merliontechs.repository.projections.SalesStats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Sales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    @Query(
            "SELECT s.date AS date, COUNT(*) AS sales " +
            "FROM Sales s " +
            "WHERE s.state = 'DELIVERED' " +
            "GROUP BY s.date"
    )
    List<SalesStats> findNumberOfSalesInStateDeliveredPerDay();

    @Query(
        "SELECT s.date AS date, COUNT(*) AS sales " +
        "FROM Sales s " +
        "GROUP BY s.date"
    )
    List<SalesStats> findTotalNumberOfSalesPerDay();
}
