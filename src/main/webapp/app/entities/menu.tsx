import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/secretarios">
        <Translate contentKey="global.menu.entities.secretarios" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cursos">
        <Translate contentKey="global.menu.entities.cursos" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/curso-disciplina">
        <Translate contentKey="global.menu.entities.cursoDisciplina" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/disciplinas">
        <Translate contentKey="global.menu.entities.disciplinas" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/professores">
        <Translate contentKey="global.menu.entities.professores" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/notas">
        <Translate contentKey="global.menu.entities.notas" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/alunos">
        <Translate contentKey="global.menu.entities.alunos" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
