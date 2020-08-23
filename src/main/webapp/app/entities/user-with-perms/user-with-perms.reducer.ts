import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserWithPerms, defaultValue } from 'app/shared/model/user-with-perms.model';

export const ACTION_TYPES = {
  FETCH_USERWITHPERMS_LIST: 'userWithPerms/FETCH_USERWITHPERMS_LIST',
  FETCH_USERWITHPERMS: 'userWithPerms/FETCH_USERWITHPERMS',
  CREATE_USERWITHPERMS: 'userWithPerms/CREATE_USERWITHPERMS',
  UPDATE_USERWITHPERMS: 'userWithPerms/UPDATE_USERWITHPERMS',
  DELETE_USERWITHPERMS: 'userWithPerms/DELETE_USERWITHPERMS',
  RESET: 'userWithPerms/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserWithPerms>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type UserWithPermsState = Readonly<typeof initialState>;

// Reducer

export default (state: UserWithPermsState = initialState, action): UserWithPermsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERWITHPERMS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERWITHPERMS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USERWITHPERMS):
    case REQUEST(ACTION_TYPES.UPDATE_USERWITHPERMS):
    case REQUEST(ACTION_TYPES.DELETE_USERWITHPERMS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USERWITHPERMS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERWITHPERMS):
    case FAILURE(ACTION_TYPES.CREATE_USERWITHPERMS):
    case FAILURE(ACTION_TYPES.UPDATE_USERWITHPERMS):
    case FAILURE(ACTION_TYPES.DELETE_USERWITHPERMS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERWITHPERMS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERWITHPERMS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERWITHPERMS):
    case SUCCESS(ACTION_TYPES.UPDATE_USERWITHPERMS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERWITHPERMS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/user-with-perms';

// Actions

export const getEntities: ICrudGetAllAction<IUserWithPerms> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USERWITHPERMS_LIST,
  payload: axios.get<IUserWithPerms>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IUserWithPerms> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERWITHPERMS,
    payload: axios.get<IUserWithPerms>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserWithPerms> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERWITHPERMS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserWithPerms> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERWITHPERMS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserWithPerms> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERWITHPERMS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
