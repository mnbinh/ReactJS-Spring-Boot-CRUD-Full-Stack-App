import fetch from 'isomorphic-fetch';
import { API } from '../config';
import queryString from 'query-string';
import { isAuth, handleResponse } from './auth';

export const createBlog = (blog, token) => {
    let createBlogEndpoint = `${API}/v1/news`;

    // if (isAuth() && isAuth().roles.length > 1) {
    //     createBlogEndpoint = `${API}/v1/news`;
    // } else {
    //     createBlogEndpoint = `${API}/v1/news`;
    // }
    blog.set('user', isAuth().id);

    return fetch(`${createBlogEndpoint}`, {
        method: 'POST',
        headers: {
            Accept: 'application/json',
            Authorization: `Bearer ${token}`
        },
        body: blog
    })
        .then(response => {
            handleResponse(response);
            return response.json();
        })
        .catch(err => console.log(err));
};

export const listBlogsWithCategoriesAndTags = (skip, limit) => {
    // const data = {
    //     limit,
    //     skip
    // };
    return fetch(`${API}/v1/news-ok`, {
        method: 'GET',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json'
        }
        // body: JSON.stringify(data)
    })
        .then(response => {
            console.log("response: " + response);
            return response.json();
        })
        .catch(err => console.log(err));
};

export const singleBlog = (slug = undefined) => {
    return fetch(`${API}/v1/news/${slug}`, {
        method: 'GET'
    })
        .then(response => {
            return response.json();
        })
        .catch(err => console.log(err));
};

export const listRelated = blog => {
    return fetch(`${API}/v1/news/related`, {
        method: 'POST',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(blog)
    })
        .then(response => {
            return response.json();
        })
        .catch(err => console.log(err));
};

export const list = username => {
    let listBlogsEndpoint;
    console.log("username:" +username);
    if (username) {
        listBlogsEndpoint = `${API}/v1/news/users/${username}`;
    } else {
        listBlogsEndpoint = `${API}/v1/news`;
    }

    return fetch(`${listBlogsEndpoint}`, {
        method: 'GET'
    })
        .then(response => {
            return response.json();
        })
        .catch(err => console.log(err));
};

export const removeBlog = (slug, token) => {
    let deleteBlogEndpoint = `${API}/v1/news/${slug}`;;

    // if (isAuth() && isAuth().roles.lenght > 1) {
    //     deleteBlogEndpoint = `${API}/v1/news/${slug}`;
    // } else if (isAuth() && isAuth().role === 0) {
    //     deleteBlogEndpoint = `${API}/v1/news/${slug}`;
    // }

    return fetch(`${deleteBlogEndpoint}`, {
        method: 'DELETE',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        }
    })
        .then(response => {
            handleResponse(response);
            return response.json();
        })
        .catch(err => console.log(err));
};

export const activeNew = (id, token) => {
    let acceptEndpoint = `${API}/v1/news-accept/${id}`;;

    return fetch(`${acceptEndpoint}`, {
        method: 'POST',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        }
    })
        .then(response => {
            handleResponse(response);
            return response.json();
        })
        .catch(err => console.log(err));
};

export const updateBlog = (blog, token, slug) => {
    let updateBlogEndpoint;

    if (isAuth() && isAuth().roles.length > 1){
        updateBlogEndpoint = `${API}/v1/news/${slug}`;
    } else if (isAuth() && isAuth().role === 0) {
        updateBlogEndpoint = `${API}/v1/news/${slug}`;
    }
    debugger;
    return fetch(`${updateBlogEndpoint}`, {
        method: 'PUT',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(blog)
    })
        .then(response => {
            handleResponse(response);
            return response.json();
        })
        .catch(err => console.log(err));
};

export const listSearch = params => {
    console.log('search params', params);
    let query = queryString.stringify(params);
    console.log('query params', query);
    return fetch(`${API}/v1/news/search?${query}`, {
        method: 'GET'
    })
        .then(response => {
            return response.json();
        })
        .catch(err => console.log(err));
};
