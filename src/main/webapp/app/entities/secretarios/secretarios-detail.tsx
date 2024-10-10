import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './secretarios.reducer';

export const SecretariosDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const secretariosEntity = useAppSelector(state => state.secretarios.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="secretariosDetailsHeading">
          <Translate contentKey="infogetApp.secretarios.detail.title">Secretarios</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{secretariosEntity.id}</dd>
          <dt>
            <span id="nomesecretario">
              <Translate contentKey="infogetApp.secretarios.nomesecretario">Nomesecretario</Translate>
            </span>
          </dt>
          <dd>{secretariosEntity.nomesecretario}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="infogetApp.secretarios.email">Email</Translate>
            </span>
          </dt>
          <dd>{secretariosEntity.email}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="infogetApp.secretarios.password">Password</Translate>
            </span>
          </dt>
          <dd>{secretariosEntity.password}</dd>
        </dl>
        <Button tag={Link} to="/secretarios" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/secretarios/${secretariosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SecretariosDetail;
