import React, { memo, useState, useEffect } from 'react';
import axios from 'axios';
import {
  ResponsiveContainer, LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
} from 'recharts';
import {
  endpointSalesInStateDeliveredPerDay,
  endpointTotalNumberOfSalesPerDay, 
  endpointCombinedSales
} from '../endpoints-stats';
import { Charts } from './type-charts-enum';

interface ChartProps {
  date: Date,
  chart: Charts
}

// The method getMonth() is 0 indexed!
const getRealMonth = (date: Date) => {
  return date.getMonth() + 1;
}

const SalesCharts = memo(({date, chart}: ChartProps) => {
  const month = getRealMonth(date);
  const year = date.getFullYear();
  const [dataToShow, setDataToShow] = useState([]);
    
  const fetchSalesData = async (endpoint) => {
    const response = await axios.get(`${endpoint}?year=${year}&month=${month}`)
    const salesResponse = response.data;
    setDataToShow(salesResponse);
  }
  
  const fetchDeliveredSales = () => 
    fetchSalesData(endpointSalesInStateDeliveredPerDay);

  const fetchDailySales = () => 
    fetchSalesData(endpointTotalNumberOfSalesPerDay);

  const fetchCombinedSales = () => 
    fetchSalesData(endpointCombinedSales);

  useEffect(() => {
    switch(chart) {
      case Charts.DAILY: fetchDailySales(); break;
      case Charts.DELIVERED: fetchDeliveredSales(); break;
      case Charts.COMBINED: fetchCombinedSales(); break;
      default : fetchDailySales()
    }
  }, [date, chart])

  const lineDeliveredSales = 
    <Line 
    dataKey="sales"
    name="ventas en estado entregado (delivered)"
    type="monotone" stroke="#ff0000"
  />

  const lineDailySales = 
    <Line 
    dataKey="sales"
    name="ventas diarias totales"
    type="monotone" stroke="#2a6a9e"
    />

  const linesCombinedSalesDaily = 
    <Line 
    dataKey="dailySales"
    name="ventas diarias totales"
    type="monotone" stroke="#2a6a9e"
    />

  const lineCombinedSalesDelivered = 
    <Line 
    dataKey="deliveredSales"
    name="ventas diarias en estado entregado (delivered)"
    type="monotone" stroke="#ff0000"
    />

  return (
    <ResponsiveContainer width={'99%'} height={300} >
        <LineChart
          data={dataToShow}
          margin={{
            top: 5, right: 30, left: 20, bottom: 5,
          }}
          >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="date" />
          <YAxis />
          <Tooltip />
          <Legend />
          { chart === Charts.DAILY && lineDailySales }
          { chart === Charts.DELIVERED && lineDeliveredSales }
          { chart === Charts.COMBINED && linesCombinedSalesDaily }
          { chart === Charts.COMBINED && lineCombinedSalesDelivered }
      </LineChart>
      </ResponsiveContainer>
  )
})

export default SalesCharts;

