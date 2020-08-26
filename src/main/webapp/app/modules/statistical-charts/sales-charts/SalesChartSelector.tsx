import React, { memo, useState, useEffect } from 'react';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
} from '@material-ui/pickers';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import DateFnsUtils from '@date-io/date-fns';
import SalesCharts from './SalesCharts';
import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      '& > *': {
        margin: theme.spacing(1),
      },
    },
  }),
);

const SalesChartSelector = memo((props) => {
    const [selectedDate, setSelectedDate] = useState(new Date());
    const originOfTime = new Date(2020, 0, 1);
    const [daily, setDaily] = useState(true);
    const [delivered, setDelivered] = useState(false);
    const [both, setBoth] = useState(false);
    const classes = useStyles();

    const handleDateChange = (date) => setSelectedDate(date);

    const handleSelectDailyChar = () => {
        setDaily(true);
        setDelivered(false);
        setBoth(false);
    }

    const handleSelectDeliveredChar = () => {
        setDelivered(true);
        setDaily(false);
        setBoth(false);
    }

    const handleSelectBothChar = () => {
        setBoth(true);
        setDaily(false);
        setDelivered(false);
    }

    return( 
        <div>
            <div className={classes.root}>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <Grid container justify="space-around">
                    <KeyboardDatePicker
                        margin="normal"
                        id="date-picker-dialog"
                        label="Seleccione el mes y el año ..."
                        format="yyyy-MM"
                        value={selectedDate}
                        onChange={handleDateChange}
                        KeyboardButtonProps={{
                            'aria-label': 'change date',
                        }}
                        disableFuture={true}
                        minDate={originOfTime}
                        views={["year", "month"]}
                        openTo="month"
                    />
                    <ButtonGroup  aria-label="outlined primary button group">
                        <Button onClick={handleSelectDailyChar}>Ventas diarias totales</Button>
                        <Button onClick={handleSelectDeliveredChar}>Ventas en estado entregado</Button>
                        <Button onClick={handleSelectBothChar}>Comparar</Button>
                    </ButtonGroup>
                </Grid>
            </MuiPickersUtilsProvider>
            </div>
            <br/>
            <SalesCharts date={selectedDate} daily={daily} delivered={delivered} both={both}/>
        </div>
    )
});

export default SalesChartSelector;