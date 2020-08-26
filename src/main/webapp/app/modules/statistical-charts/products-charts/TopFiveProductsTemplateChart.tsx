import React, { memo, useState, useEffect } from 'react';
import axios from 'axios';
import {
    ResponsiveContainer, BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
  } from 'recharts';
import { Typography } from '@material-ui/core';

interface TopFiveProductsChartProps {
    endpoint: string,
    barName: string,
    barDataKey: string,
    charTitle: string
}

const TopFiveProductsTemplateChart = memo(
    ({endpoint, barName, barDataKey, charTitle}: TopFiveProductsChartProps) => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get(endpoint);
            const productsResponse = response.data;
            setProducts(productsResponse);
        }
        fetchData();
    }, [])

    return( 
        <div>
            <Typography variant="h5" align="center">{charTitle}</Typography>
            <br/>
            <ResponsiveContainer width={'99%'} height={300} >
                <BarChart
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
                    <Bar name={barName} dataKey={barDataKey} fill="#2a6a9e" />
                </BarChart>
            </ResponsiveContainer>
        </div>
    )
});

export default TopFiveProductsTemplateChart;