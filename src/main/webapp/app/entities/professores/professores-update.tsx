import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './professores.reducer';

export const ProfessoresUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const professoresEntity = useAppSelector(state => state.professores.entity);
  const loading = useAppSelector(state => state.professores.loading);
  const updating = useAppSelector(state => state.professores.updating);
  const updateSuccess = useAppSelector(state => state.professores.updateSuccess);

  const handleClose = () => {
    navigate('/professores');
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
      ...professoresEntity,
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
          ...professoresEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="infogetApp.professores.home.createOrEditLabel" data-cy="ProfessoresCreateUpdateHeading">
            <Translate contentKey="infogetApp.professores.home.createOrEditLabel">Create or edit a Professores</Translate>
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
                  id="professores-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('infogetApp.professores.nomeprofessor')}
                id="professores-nomeprofessor"
                name="nomeprofessor"
                data-cy="nomeprofessor"
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
                label={translate('infogetApp.professores.email')}
                id="professores-email"
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
                label={translate('infogetApp.professores.password')}
                id="professores-password"
                name="password"
                data-cy="password"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/professores" replace color="info">
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

export default ProfessoresUpdate;
