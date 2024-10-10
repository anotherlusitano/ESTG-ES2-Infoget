import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './professores.reducer';

export const ProfessoresDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const professoresEntity = useAppSelector(state => state.professores.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="professoresDetailsHeading">
          <Translate contentKey="infogetApp.professores.detail.title">Professores</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{professoresEntity.id}</dd>
          <dt>
            <span id="nomeprofessor">
              <Translate contentKey="infogetApp.professores.nomeprofessor">Nomeprofessor</Translate>
            </span>
          </dt>
          <dd>{professoresEntity.nomeprofessor}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="infogetApp.professores.email">Email</Translate>
            </span>
          </dt>
          <dd>{professoresEntity.email}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="infogetApp.professores.password">Password</Translate>
            </span>
          </dt>
          <dd>{professoresEntity.password}</dd>
        </dl>
        <Button tag={Link} to="/professores" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/professores/${professoresEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfessoresDetail;
