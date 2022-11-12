import dayjs from 'dayjs';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';

export interface ITransaction {
  id?: number;
  date?: string;
  type?: TransactionType;
  primaryCategory?: string;
  secondaryCategory?: string | null;
  amount?: number;
  due?: number | null;
  title?: string | null;
  description?: string | null;
  tags?: string | null;
}

export const defaultValue: Readonly<ITransaction> = {};
