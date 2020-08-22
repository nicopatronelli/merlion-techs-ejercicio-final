import React, { memo, useState, useEffect } from 'react';
import axios from 'axios';
import {
    BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
  } from 'recharts';

const TotalSalesPerDayChart = memo((props) => {
    const [sales, setSales] = useState([]);

    useEffect(() => {

        const fetchData = async () => {
            const response = await axios.get('api/stats/sales/daily');
            const salesResponse = response.data;
            setSales(salesResponse);
        }
        fetchData();
    }, [])

    return(
            <BarChart
                width={500}
                height={300}
                data={sales}
                margin={{
                    top: 5, right: 30, left: 20, bottom: 5,
                }}
            >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar name="ventas diarias" dataKey="sales" fill="#82ca9d" />
            </BarChart>
    )
});

export default TotalSalesPerDayChart;