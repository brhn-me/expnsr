import dayjs from 'dayjs';
import { IFixedDeposit } from 'app/shared/model/fixed-deposit.model';

export interface IInterest {
  id?: number;
  date?: string;
  interestRate?: number;
  taxRate?: number;
  amount?: number;
  tax?: number;
  verified?: boolean | null;
  fixedDeposit?: IFixedDeposit | null;
}

export const defaultValue: Readonly<IInterest> = {
  verified: false,
};
