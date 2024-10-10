import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProfessores } from 'app/entities/professores/professores.reducer';
import { createEntity, getEntity, reset, updateEntity } from './disciplinas.reducer';

export const DisciplinasUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const professores = useAppSelector(state => state.professores.entities);
  const disciplinasEntity = useAppSelector(state => state.disciplinas.entity);
  const loading = useAppSelector(state => state.disciplinas.loading);
  const updating = useAppSelector(state => state.disciplinas.updating);
  const updateSuccess = useAppSelector(state => state.disciplinas.updateSuccess);

  const handleClose = () => {
    navigate('/disciplinas');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProfessores({}));
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
    if (values.cargahoraria !== undefined && typeof values.cargahoraria !== 'number') {
      values.cargahoraria = Number(values.cargahoraria);
    }

    const entity = {
      ...disciplinasEntity,
      ...values,
      professores: professores.find(it => it.id.toString() === values.professores?.toString()),
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
          ...disciplinasEntity,
          professores: disciplinasEntity?.professores?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="infogetApp.disciplinas.home.createOrEditLabel" data-cy="DisciplinasCreateUpdateHeading">
            <Translate contentKey="infogetApp.disciplinas.home.createOrEditLabel">Create or edit a Disciplinas</Translate>
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
                  id="disciplinas-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('infogetApp.disciplinas.nomedisciplina')}
                id="disciplinas-nomedisciplina"
                name="nomedisciplina"
                data-cy="nomedisciplina"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('infogetApp.disciplinas.cargahoraria')}
                id="disciplinas-cargahoraria"
                name="cargahoraria"
                data-cy="cargahoraria"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="disciplinas-professores"
                name="professores"
                data-cy="professores"
                label={translate('infogetApp.disciplinas.professores')}
                type="select"
              >
                <option value="" key="0" />
                {professores
                  ? professores.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/disciplinas" replace color="info">
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

export default DisciplinasUpdate;
