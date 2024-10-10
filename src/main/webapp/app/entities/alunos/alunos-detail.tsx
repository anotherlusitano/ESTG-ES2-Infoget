import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './alunos.reducer';

export const AlunosDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const alunosEntity = useAppSelector(state => state.alunos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alunosDetailsHeading">
          <Translate contentKey="infogetApp.alunos.detail.title">Alunos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{alunosEntity.id}</dd>
          <dt>
            <span id="nomealuno">
              <Translate contentKey="infogetApp.alunos.nomealuno">Nomealuno</Translate>
            </span>
          </dt>
          <dd>{alunosEntity.nomealuno}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="infogetApp.alunos.email">Email</Translate>
            </span>
          </dt>
          <dd>{alunosEntity.email}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="infogetApp.alunos.password">Password</Translate>
            </span>
          </dt>
          <dd>{alunosEntity.password}</dd>
          <dt>
            <Translate contentKey="infogetApp.alunos.cursos">Cursos</Translate>
          </dt>
          <dd>{alunosEntity.cursos ? alunosEntity.cursos.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/alunos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alunos/${alunosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlunosDetail;
