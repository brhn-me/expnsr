import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/fixed-deposit">
        Fixed Deposit
      </MenuItem>
      <MenuItem icon="asterisk" to="/interest">
        Interest
      </MenuItem>
      <MenuItem icon="asterisk" to="/deposit">
        Deposit
      </MenuItem>
      <MenuItem icon="asterisk" to="/recurring-bills">
        Recurring Bills
      </MenuItem>
      <MenuItem icon="asterisk" to="/transaction">
        Transaction
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
