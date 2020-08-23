import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = (props: {isAdmin: boolean}) => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/product">
      <Translate contentKey="global.menu.entities.product" />
    </MenuItem>
    {props.isAdmin && 
    <span>
      <MenuItem icon="asterisk" to="/user-with-perms">
        <Translate contentKey="global.menu.entities.userWithPerms" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/permissions">
        <Translate contentKey="global.menu.entities.permissions" />
      </MenuItem> 
      </span>
      }
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
