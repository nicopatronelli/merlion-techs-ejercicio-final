package merliontechs.web.rest;

import merliontechs.repository.ProductRepository;
import merliontechs.repository.projections.ProductBestSelling;
import merliontechs.repository.projections.ProductMostProfitable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional
public class ProductStatsController {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "product";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductRepository productRepository;

    public ProductStatsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/stats/products/five-best-sellings")
    public List<ProductBestSelling> getFiveProductsBestSeller() {
        return productRepository.findFiveBestSellingProducts();
    }

    @GetMapping("/stats/products/five-most-profitable")
    public List<ProductMostProfitable> getFiveMostProfitableProducts() {
        return productRepository.findFiveMostProfitableProducts();
    }

}
