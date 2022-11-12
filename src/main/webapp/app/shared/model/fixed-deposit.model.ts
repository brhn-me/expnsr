import dayjs from 'dayjs';
import { IInterest } from 'app/shared/model/interest.model';
import { IDeposit } from 'app/shared/model/deposit.model';
import { FDType } from 'app/shared/model/enumerations/fd-type.model';
import { FDStatus } from 'app/shared/model/enumerations/fd-status.model';

export interface IFixedDeposit {
  id?: string;
  type?: FDType;
  issueDate?: string;
  lastRenewDate?: string | null;
  maturityDate?: string;
  interestRate?: number;
  taxRate?: number;
  tenure?: number;
  interestTenure?: number;
  amount?: number;
  maturityAmount?: number;
  tax?: number;
  monthlyDeposit?: number;
  monthlyDepositDate?: string | null;
  bank?: string;
  autoRenew?: boolean;
  renewWithInterest?: boolean;
  status?: FDStatus;
  interests?: IInterest[] | null;
  deposits?: IDeposit[] | null;
}

export const defaultValue: Readonly<IFixedDeposit> = {
  autoRenew: false,
  renewWithInterest: false,
};
