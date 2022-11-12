export interface IRecurringBills {
  id?: number;
  amount?: number;
  tenure?: number;
  primaryCategory?: string;
  secondaryCategory?: string | null;
}

export const defaultValue: Readonly<IRecurringBills> = {};
