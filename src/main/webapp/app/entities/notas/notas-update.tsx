import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getAlunos } from 'app/entities/alunos/alunos.reducer';
import { getEntities as getDisciplinas } from 'app/entities/disciplinas/disciplinas.reducer';
import { createEntity, getEntity, reset, updateEntity } from './notas.reducer';

export const NotasUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const alunos = useAppSelector(state => state.alunos.entities);
  const disciplinas = useAppSelector(state => state.disciplinas.entities);
  const notasEntity = useAppSelector(state => state.notas.entity);
  const loading = useAppSelector(state => state.notas.loading);
  const updating = useAppSelector(state => state.notas.updating);
  const updateSuccess = useAppSelector(state => state.notas.updateSuccess);

  const handleClose = () => {
    navigate('/notas');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAlunos({}));
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
    if (values.nota !== undefined && typeof values.nota !== 'number') {
      values.nota = Number(values.nota);
    }

    const entity = {
      ...notasEntity,
      ...values,
      alunos: alunos.find(it => it.id.toString() === values.alunos?.toString()),
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
          ...notasEntity,
          alunos: notasEntity?.alunos?.id,
          disciplinas: notasEntity?.disciplinas?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="infogetApp.notas.home.createOrEditLabel" data-cy="NotasCreateUpdateHeading">
            <Translate contentKey="infogetApp.notas.home.createOrEditLabel">Create or edit a Notas</Translate>
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
                  id="notas-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('infogetApp.notas.nota')}
                id="notas-nota"
                name="nota"
                data-cy="nota"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  max: { value: 20, message: translate('entity.validation.max', { max: 20 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField id="notas-alunos" name="alunos" data-cy="alunos" label={translate('infogetApp.notas.alunos')} type="select">
                <option value="" key="0" />
                {alunos
                  ? alunos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="notas-disciplinas"
                name="disciplinas"
                data-cy="disciplinas"
                label={translate('infogetApp.notas.disciplinas')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notas" replace color="info">
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

export default NotasUpdate;
