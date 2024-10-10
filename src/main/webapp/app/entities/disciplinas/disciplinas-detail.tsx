import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './disciplinas.reducer';

export const DisciplinasDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const disciplinasEntity = useAppSelector(state => state.disciplinas.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="disciplinasDetailsHeading">
          <Translate contentKey="infogetApp.disciplinas.detail.title">Disciplinas</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{disciplinasEntity.id}</dd>
          <dt>
            <span id="nomedisciplina">
              <Translate contentKey="infogetApp.disciplinas.nomedisciplina">Nomedisciplina</Translate>
            </span>
          </dt>
          <dd>{disciplinasEntity.nomedisciplina}</dd>
          <dt>
            <span id="cargahoraria">
              <Translate contentKey="infogetApp.disciplinas.cargahoraria">Cargahoraria</Translate>
            </span>
          </dt>
          <dd>{disciplinasEntity.cargahoraria}</dd>
          <dt>
            <Translate contentKey="infogetApp.disciplinas.professores">Professores</Translate>
          </dt>
          <dd>{disciplinasEntity.professores ? disciplinasEntity.professores.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/disciplinas" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/disciplinas/${disciplinasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DisciplinasDetail;
