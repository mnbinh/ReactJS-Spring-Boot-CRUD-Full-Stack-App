import { useState, useEffect } from 'react';
import { signin, authenticate, isAuth } from '../../actions/auth';
import Router from 'next/router';

const SigninComponent = () => {
    const [values, setValues] = useState({
        name: 'An',
        email: 'mod@gmail.com',
        username: 'mod',
        password: 'abc123',
        error: '',
        loading: false,
        message: '',
        showForm: true
    });

    const { email, username,password, error, loading, message, showForm } = values;

    useEffect(() => {
        isAuth() && Router.push(`/`);
    }, []);

    const handleSubmit = e => {
        e.preventDefault();
        // console.table({ name, email, password, error, loading, message, showForm });
        setValues({ ...values, loading: true, error: false });
        const user = { username, password };

        signin(user).then(data => {
            if (!data) {
                setValues({ ...values, error: 'Somgthing wrong happen', loading: false });
            } else {
                // save user token to cookie
                // save user info to localstorage
                // authenticate user

                authenticate(data, () => {
                    if(isAuth()  && isAuth().roles == undefined){
                        setValues({ ...values, error: 'Can not log in', loading: false });
                    }
                    else if (isAuth() && isAuth().roles.length > 1) {
                        Router.push(`/admin`);
                    } else {
                        Router.push(`/user`);
                    }
                });
            }
        });
    };

    const handleChange = name => e => {
        setValues({ ...values, error: false, [name]: e.target.value });
    };

    const showLoading = () => (loading ? <div className="alert alert-info">Loading...</div> : '');
    const showError = () => (error ? <div className="alert alert-danger">{error}</div> : '');
    const showMessage = () => (message ? <div className="alert alert-info">{message}</div> : '');

    const signinForm = () => {
        return (
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <input
                        value={username}
                        onChange={handleChange('username')}
                        type="username"
                        className="form-control"
                        placeholder="Type your username"
                    />
                </div>

                <div className="form-group">
                    <input
                        value={password}
                        onChange={handleChange('password')}
                        type="password"
                        className="form-control"
                        placeholder="Type your password"
                    />
                </div>

                <div>
                    <button className="btn btn-primary">Signin</button>
                </div>
            </form>
        );
    };

    return (
        <React.Fragment>
            {showError()}
            {showLoading()}
            {showMessage()}
            {showForm && signinForm()}
        </React.Fragment>
    );
};

export default SigninComponent;
