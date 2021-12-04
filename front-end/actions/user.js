import fetch from 'isomorphic-fetch';
import { API } from '../config';
import { handleResponse , isAuth} from './auth';

export const userPublicProfile = username => {
    return fetch(`${API}/v1/user/${username}`, {
        method: 'GET',
        headers: {
            Accept: 'application/json'
        }
    })
        .then(response => {
            return response.json();
        })
        .catch(err => console.log(err));
};

export const getProfile = (token) => {
    return fetch(`${API}/v1/user/${isAuth().id}`, {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            Authorization: `Bearer ${token}`
        }
    })
        .then(response => {
            return response.json();
        })
        .catch(err => console.log(err));
};

export const update = (token, user) => {
    return fetch(`${API}/v1/user/${isAuth().id}`, {
        method: 'PUT',
        headers: {
            Accept: 'application/json',
            Authorization: `Bearer ${token}`
        },
        body: user
    })
        .then(response => {
            handleResponse(response);
            return response.json();
        })
        .catch(err => console.log(err));
};
