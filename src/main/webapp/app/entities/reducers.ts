import fixedDeposit from 'app/entities/fixed-deposit/fixed-deposit.reducer';
import interest from 'app/entities/interest/interest.reducer';
import deposit from 'app/entities/deposit/deposit.reducer';
import recurringBills from 'app/entities/recurring-bills/recurring-bills.reducer';
import transaction from 'app/entities/transaction/transaction.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  fixedDeposit,
  interest,
  deposit,
  recurringBills,
  transaction,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
