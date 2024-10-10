import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notas.reducer';

export const NotasDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notasEntity = useAppSelector(state => state.notas.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notasDetailsHeading">
          <Translate contentKey="infogetApp.notas.detail.title">Notas</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{notasEntity.id}</dd>
          <dt>
            <span id="nota">
              <Translate contentKey="infogetApp.notas.nota">Nota</Translate>
            </span>
          </dt>
          <dd>{notasEntity.nota}</dd>
          <dt>
            <Translate contentKey="infogetApp.notas.alunos">Alunos</Translate>
          </dt>
          <dd>{notasEntity.alunos ? notasEntity.alunos.id : ''}</dd>
          <dt>
            <Translate contentKey="infogetApp.notas.disciplinas">Disciplinas</Translate>
          </dt>
          <dd>{notasEntity.disciplinas ? notasEntity.disciplinas.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notas" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notas/${notasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotasDetail;
