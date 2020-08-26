import React, { memo, useState, useEffect } from 'react';
import axios from 'axios';
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
} from 'recharts';
import {
  endpointSalesInStateDeliveredPerDay,
  endpointTotalNumberOfSalesPerDay, 
  endpointCombinedSales
} from '../endpoints-stats';

interface ChartProps {
  daily: boolean,
  delivered: boolean,
  date: Date
  both: boolean
}

// The method getMonth() is 0 indexed!
const getRealMonth = (date: Date) => {
  return date.getMonth() + 1;
}

const SalesCharts = memo(({date, daily, delivered, both}: ChartProps) => {
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
    if (daily) fetchDailySales();
    else if (delivered) fetchDeliveredSales();
    else if (both) fetchCombinedSales();
  }, [date, daily, delivered, both])

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
        <LineChart
          width={1050}
          height={300}
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
          { delivered && lineDeliveredSales }
          { daily && lineDailySales }
          { both && linesCombinedSalesDaily }
          { both && lineCombinedSalesDelivered }
      </LineChart>
  )
})

export default SalesCharts;

