import React, { memo, useState, useEffect } from 'react';
import axios from 'axios';
import {
    BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
  } from 'recharts';

const TopFiveMostRevenueProductsChart = memo((props) => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get('api/stats/products/most-renevue');
            const productsResponse = response.data;
            setProducts(productsResponse);
        }
        fetchData();
    }, [])

    return(
            <BarChart
                width={800}
                height={300}
                data={products}
                margin={{
                    top: 5, right: 30, left: 20, bottom: 5,
                }}
            >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar name="ganancias(USD)" dataKey="profits" fill="#4089c1" />
            </BarChart>
    )
});

export default TopFiveMostRevenueProductsChart;