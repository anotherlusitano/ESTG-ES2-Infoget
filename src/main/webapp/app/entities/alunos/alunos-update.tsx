import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCursos } from 'app/entities/cursos/cursos.reducer';
import { createEntity, getEntity, reset, updateEntity } from './alunos.reducer';

export const AlunosUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cursos = useAppSelector(state => state.cursos.entities);
  const alunosEntity = useAppSelector(state => state.alunos.entity);
  const loading = useAppSelector(state => state.alunos.loading);
  const updating = useAppSelector(state => state.alunos.updating);
  const updateSuccess = useAppSelector(state => state.alunos.updateSuccess);

  const handleClose = () => {
    navigate('/alunos');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCursos({}));
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
      ...alunosEntity,
      ...values,
      cursos: cursos.find(it => it.id.toString() === values.cursos?.toString()),
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
          ...alunosEntity,
          cursos: alunosEntity?.cursos?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="infogetApp.alunos.home.createOrEditLabel" data-cy="AlunosCreateUpdateHeading">
            <Translate contentKey="infogetApp.alunos.home.createOrEditLabel">Create or edit a Alunos</Translate>
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
                  id="alunos-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('infogetApp.alunos.nomealuno')}
                id="alunos-nomealuno"
                name="nomealuno"
                data-cy="nomealuno"
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
                label={translate('infogetApp.alunos.email')}
                id="alunos-email"
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
                label={translate('infogetApp.alunos.password')}
                id="alunos-password"
                name="password"
                data-cy="password"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="alunos-cursos" name="cursos" data-cy="cursos" label={translate('infogetApp.alunos.cursos')} type="select">
                <option value="" key="0" />
                {cursos
                  ? cursos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/alunos" replace color="info">
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

export default AlunosUpdate;
