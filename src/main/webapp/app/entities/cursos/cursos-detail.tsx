import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cursos.reducer';

export const CursosDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cursosEntity = useAppSelector(state => state.cursos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cursosDetailsHeading">
          <Translate contentKey="infogetApp.cursos.detail.title">Cursos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cursosEntity.id}</dd>
          <dt>
            <span id="nomecurso">
              <Translate contentKey="infogetApp.cursos.nomecurso">Nomecurso</Translate>
            </span>
          </dt>
          <dd>{cursosEntity.nomecurso}</dd>
        </dl>
        <Button tag={Link} to="/cursos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cursos/${cursosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CursosDetail;
