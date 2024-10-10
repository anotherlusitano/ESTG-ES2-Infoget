import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './curso-disciplina.reducer';

export const CursoDisciplinaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cursoDisciplinaEntity = useAppSelector(state => state.cursoDisciplina.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cursoDisciplinaDetailsHeading">
          <Translate contentKey="infogetApp.cursoDisciplina.detail.title">CursoDisciplina</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cursoDisciplinaEntity.id}</dd>
          <dt>
            <Translate contentKey="infogetApp.cursoDisciplina.cursos">Cursos</Translate>
          </dt>
          <dd>{cursoDisciplinaEntity.cursos ? cursoDisciplinaEntity.cursos.id : ''}</dd>
          <dt>
            <Translate contentKey="infogetApp.cursoDisciplina.disciplinas">Disciplinas</Translate>
          </dt>
          <dd>{cursoDisciplinaEntity.disciplinas ? cursoDisciplinaEntity.disciplinas.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/curso-disciplina" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/curso-disciplina/${cursoDisciplinaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CursoDisciplinaDetail;
