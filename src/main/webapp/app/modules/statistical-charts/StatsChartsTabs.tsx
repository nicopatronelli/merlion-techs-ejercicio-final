import React from 'react';
import { makeStyles, Theme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import SalesChartSelector from './sales-charts/SalesChartSelector';
import ProductCharts from './products-charts/ProductCharts';

interface TabPanelProps {
  children?: React.ReactNode;
  index: any;
  value: any;
}

const TabPanel = (props: TabPanelProps) => {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

function a11yProps(index: any) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
  },
  customTwo: {
    flexGrow: 1,
    padding: '0rem',
    color: '#484848',
    backgroundColor: 'white',
    fontFamily: 'Open Sans',
    fontSize: '1rem',
},
}));

const StatsChartsTabs = () => {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  const handleChange = (event: React.ChangeEvent<{}>, newValue: number) => {
    setValue(newValue);
  };

  return (
    <div className={classes.root}>
      <p className="lead">
        Puede seleccionar estadísticas de ventas, que 
        se calculan a nivel mensual, y estadísticas 
        sobre productos, que involucran datos históricos.
      </p>
      <AppBar position="static">
        <Tabs indicatorColor="primary" className={classes.customTwo} value={value} onChange={handleChange} >
          <Tab label="Estadisticas de Ventas" {...a11yProps(0)} />
          <Tab label="Estadísticas de Productos" {...a11yProps(1)} />
        </Tabs>
      </AppBar>
      <TabPanel value={value} index={0}>
        <SalesChartSelector />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <ProductCharts />
      </TabPanel>
    </div>
  );
}

export default StatsChartsTabs;