import React, { memo, useState, useEffect } from 'react';
import axios from 'axios';
import {
    BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
  } from 'recharts';

const TopFiveSellingProductsChart = memo((props) => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get('api/stats/products/best-sellings');
            const productsResponse = response.data;
            setProducts(productsResponse);
        }
        fetchData();
    }, [])

    return(
        <div>
            <h3>Cinco productos más vendidos</h3>
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
                <Bar name="ventas" dataKey="sales" fill="#4089c1" />
            </BarChart>
        </div>
    )
});

export default TopFiveSellingProductsChart;