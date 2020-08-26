package merliontechs.repository;

import merliontechs.domain.Sales;
import merliontechs.repository.projections.SalesStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    @Query(
        value = "SELECT s.date AS date, COUNT(*) AS sales " +
                "FROM Sales s " +
                "WHERE s.state = 'DELIVERED' " +
                    "AND EXTRACT(YEAR FROM s.date) = :year " +
                    "AND EXTRACT(MONTH FROM s.date) = :month " +
                "GROUP BY s.date",
        nativeQuery = true
    )
    List<SalesStats> findNumberOfSalesInStateDeliveredPerDay(
        @Param("year") int year,
        @Param("month") int month
    );

    @Query(
        value = "SELECT s.date AS date, COUNT(*) AS sales " +
                "FROM Sales s " +
                "WHERE EXTRACT(YEAR FROM s.date) = :year " +
                    "AND EXTRACT(MONTH FROM s.date) = :month " +
                "GROUP BY s.date",
        nativeQuery = true
    )
    List<SalesStats> findTotalNumberOfSalesPerDay(
        @Param("year") int year,
        @Param("month") int month
    );
}
