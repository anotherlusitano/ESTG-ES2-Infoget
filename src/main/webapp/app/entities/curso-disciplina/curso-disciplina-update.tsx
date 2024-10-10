import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCursos } from 'app/entities/cursos/cursos.reducer';
import { getEntities as getDisciplinas } from 'app/entities/disciplinas/disciplinas.reducer';
import { createEntity, getEntity, reset, updateEntity } from './curso-disciplina.reducer';

export const CursoDisciplinaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cursos = useAppSelector(state => state.cursos.entities);
  const disciplinas = useAppSelector(state => state.disciplinas.entities);
  const cursoDisciplinaEntity = useAppSelector(state => state.cursoDisciplina.entity);
  const loading = useAppSelector(state => state.cursoDisciplina.loading);
  const updating = useAppSelector(state => state.cursoDisciplina.updating);
  const updateSuccess = useAppSelector(state => state.cursoDisciplina.updateSuccess);

  const handleClose = () => {
    navigate('/curso-disciplina');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCursos({}));
    dispatch(getDisciplinas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...cursoDisciplinaEntity,
      ...values,
      cursos: cursos.find(it => it.id.toString() === values.cursos?.toString()),
      disciplinas: disciplinas.find(it => it.id.toString() === values.disciplinas?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...cursoDisciplinaEntity,
          cursos: cursoDisciplinaEntity?.cursos?.id,
          disciplinas: cursoDisciplinaEntity?.disciplinas?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="infogetApp.cursoDisciplina.home.createOrEditLabel" data-cy="CursoDisciplinaCreateUpdateHeading">
            <Translate contentKey="infogetApp.cursoDisciplina.home.createOrEditLabel">Create or edit a CursoDisciplina</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="curso-disciplina-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="curso-disciplina-cursos"
                name="cursos"
                data-cy="cursos"
                label={translate('infogetApp.cursoDisciplina.cursos')}
                type="select"
              >
                <option value="" key="0" />
                {cursos
                  ? cursos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="curso-disciplina-disciplinas"
                name="disciplinas"
                data-cy="disciplinas"
                label={translate('infogetApp.cursoDisciplina.disciplinas')}
                type="select"
              >
                <option value="" key="0" />
                {disciplinas
                  ? disciplinas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/curso-disciplina" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CursoDisciplinaUpdate;
