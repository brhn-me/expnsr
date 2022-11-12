import dayjs from 'dayjs';
import { IFixedDeposit } from 'app/shared/model/fixed-deposit.model';

export interface IDeposit {
  id?: number;
  date?: string;
  amount?: number;
  verified?: boolean | null;
  fixedDeposit?: IFixedDeposit | null;
}

export const defaultValue: Readonly<IDeposit> = {
  verified: false,
};
