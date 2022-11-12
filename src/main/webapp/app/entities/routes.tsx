import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FixedDeposit from './fixed-deposit';
import Interest from './interest';
import Deposit from './deposit';
import RecurringBills from './recurring-bills';
import Transaction from './transaction';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="fixed-deposit/*" element={<FixedDeposit />} />
        <Route path="interest/*" element={<Interest />} />
        <Route path="deposit/*" element={<Deposit />} />
        <Route path="recurring-bills/*" element={<RecurringBills />} />
        <Route path="transaction/*" element={<Transaction />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
