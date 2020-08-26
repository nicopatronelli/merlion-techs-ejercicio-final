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
    charTitle="Los cinco productos más vendidos"
    />

const topFiveProductsMostProfitableChart = 
    <TopFiveProductsTemplateChart
    endpoint={endpointTopFiveMostProfitableProducts}
    barName="ganancias(USD)" 
    barDataKey="profits"
    charTitle="Los cinco productos de mayor ganancia"
    />

const ProductCharts = () => {
    return (
        <div>
            <br/>
            {topFiveBestSellingProductsChart}
            <br/>
            <br/>
            {topFiveProductsMostProfitableChart}
            <br/>
        </div>
    )
}

export default ProductCharts;
