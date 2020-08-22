import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';
import { State } from 'app/shared/model/enumerations/state.model';

export interface ISalesStatitics {
  date?: string;
  amount?: number;
}

export const defaultValue: Readonly<ISalesStatitics> = {};
