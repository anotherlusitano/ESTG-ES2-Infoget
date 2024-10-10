import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './disciplinas.reducer';

export const Disciplinas = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const disciplinasList = useAppSelector(state => state.disciplinas.entities);
  const loading = useAppSelector(state => state.disciplinas.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="disciplinas-heading" data-cy="DisciplinasHeading">
        <Translate contentKey="infogetApp.disciplinas.home.title">Disciplinas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="infogetApp.disciplinas.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/disciplinas/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="infogetApp.disciplinas.home.createLabel">Create new Disciplinas</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {disciplinasList && disciplinasList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="infogetApp.disciplinas.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('nomedisciplina')}>
                  <Translate contentKey="infogetApp.disciplinas.nomedisciplina">Nomedisciplina</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nomedisciplina')} />
                </th>
                <th className="hand" onClick={sort('cargahoraria')}>
                  <Translate contentKey="infogetApp.disciplinas.cargahoraria">Cargahoraria</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cargahoraria')} />
                </th>
                <th>
                  <Translate contentKey="infogetApp.disciplinas.professores">Professores</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {disciplinasList.map((disciplinas, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/disciplinas/${disciplinas.id}`} color="link" size="sm">
                      {disciplinas.id}
                    </Button>
                  </td>
                  <td>{disciplinas.nomedisciplina}</td>
                  <td>{disciplinas.cargahoraria}</td>
                  <td>
                    {disciplinas.professores ? (
                      <Link to={`/professores/${disciplinas.professores.id}`}>{disciplinas.professores.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/disciplinas/${disciplinas.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/disciplinas/${disciplinas.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/disciplinas/${disciplinas.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="infogetApp.disciplinas.home.notFound">No Disciplinas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Disciplinas;
