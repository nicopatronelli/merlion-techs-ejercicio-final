import React from 'react';
import TopFiveProductsTemplateChart from './TopFiveProductsTemplateChart';
import {
  endpointTopFiveBestSellingProducts, 
  endpointTopFiveMostProfitableProducts
} from '../endpoints-stats'

const topFiveBestSellingProductsChart = 
    <TopFiveProductsTemplateChart
    endpoint={endpointTopFiveBestSellingProducts}
    barName="ventas" 
    barDataKey="sales"
    charTitle="Cinco productos más vendidos"
    />

const topFiveProductsMostProfitableChart = 
    <TopFiveProductsTemplateChart
    endpoint={endpointTopFiveMostProfitableProducts}
    barName="ganancias(USD)" 
    barDataKey="profits"
    charTitle="Cinco productos de mayor ganancia"
    />

const ProductStatsCharts = () => {
    return (
        <div>
            <br/>
            {topFiveBestSellingProductsChart}
            <br/>
            {topFiveProductsMostProfitableChart}
            <br/>
        </div>
    )
}

export default ProductStatsCharts;
