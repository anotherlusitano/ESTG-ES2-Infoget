import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './secretarios.reducer';

export const SecretariosUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const secretariosEntity = useAppSelector(state => state.secretarios.entity);
  const loading = useAppSelector(state => state.secretarios.loading);
  const updating = useAppSelector(state => state.secretarios.updating);
  const updateSuccess = useAppSelector(state => state.secretarios.updateSuccess);

  const handleClose = () => {
    navigate('/secretarios');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...secretariosEntity,
      ...values,
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
          ...secretariosEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="infogetApp.secretarios.home.createOrEditLabel" data-cy="SecretariosCreateUpdateHeading">
            <Translate contentKey="infogetApp.secretarios.home.createOrEditLabel">Create or edit a Secretarios</Translate>
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
                  id="secretarios-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('infogetApp.secretarios.nomesecretario')}
                id="secretarios-nomesecretario"
                name="nomesecretario"
                data-cy="nomesecretario"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: {
                    value:
                      /^[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+(?:\s[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+)*(?:\s(?:da|do|dos|das|[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+))?(?:\s[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+)*$/,
                    message: translate('entity.validation.pattern', {
                      pattern:
                        '^[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+(?:\\s[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+)*(?:\\s(?:da|do|dos|das|[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+))?(?:\\s[A-Z][a-zA-Zà-úÀ-ÚãÃõÕ]+)*$',
                    }),
                  },
                }}
              />
              <ValidatedField
                label={translate('infogetApp.secretarios.email')}
                id="secretarios-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: {
                    value: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/,
                    message: translate('entity.validation.pattern', { pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('infogetApp.secretarios.password')}
                id="secretarios-password"
                name="password"
                data-cy="password"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/secretarios" replace color="info">
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

export default SecretariosUpdate;
