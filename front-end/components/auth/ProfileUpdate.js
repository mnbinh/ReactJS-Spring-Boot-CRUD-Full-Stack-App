import Link from 'next/link';
import { useState, useEffect } from 'react';
import Router from 'next/router';
import { getCookie, isAuth, updateUser } from '../../actions/auth';
import { getProfile, update } from '../../actions/user';
import { API } from '../../config';

const ProfileUpdate = () => {
    const [values, setValues] = useState({
        username: '',
        firstName: '',
        email: '',
        lastName: '',
        mobile: '',
        error: false,
        success: false,
        loading: false,
        userData: ''
    });

    const token = getCookie('token');
    const { username, firstName,lastName,mobile, email, error, success, loading, photo, userData } = values;

    const init = () => {
        getProfile(token).then(data => {
            if (data.error) {
                setValues({ ...values, error: data.error });
            } else {
                setValues({
                    ...values,
                    userData: new FormData(),
                    username: data.user.userName,
                    firstName: data.user.firstName,
                    email: data.user.email,
                    lastName: data.user.lastName,
                    mobile:  data.user.mobile
                });
            }
        });
    };

    useEffect(() => {
        init();
    }, []);

    const handleChange = name => e => {
        // let userFormData = new FormData();
        userData.set(name, e.target.value);
        setValues({ ...values, [name]:  e.target.value, error: false, success: false });
    };

    const handleSubmit = e => {
        e.preventDefault();
        setValues({ ...values, loading: true });
        update(token, userData).then(data => {
            if (data.error) {
                setValues({ ...values, error: data.error, success: false, loading: false });
            } else {
                updateUser(data, () => {
                    setValues({
                        ...values,
                        username: data.userName,
                        firstName: data.firstName,
                        lastName: data.lastName,
                        mobile:  data.mobile,
                        success: true,
                        loading: false
                    });
                });
            }
        });
    };

    const profileUpdateForm = () => (
        <form onSubmit={handleSubmit}>
            {/* 
            <div className="form-group">
                <label className="btn btn-outline-info">
                    Profile photo
                    <input onChange={handleChange('photo')} type="file" accept="image/*" hidden />
                </label>
            </div>
            <div className="form-group">
                <label className="text-muted">Username</label>
                <input onChange={handleChange('username')} type="text" value={username} className="form-control" />
            </div>
            <div className="form-group">
                <label className="text-muted">Name</label>
                <input onChange={handleChange('name')} type="text" value={name} className="form-control" />
            </div>
            <div className="form-group">
                <label className="text-muted">Email</label>
                <input onChange={handleChange('email')} type="text" value={email} className="form-control" />
            </div>
            */}
            <div className="form-group">
                <label className="text-muted">First Name</label>
                <input onChange={handleChange('firstName')} type="text" value={firstName} className="form-control" />
            </div>
            <div className="form-group">
                <label className="text-muted">Last Name</label>
                <input onChange={handleChange('lastName')} type="text" value={lastName} className="form-control" />
            </div>
            <div className="form-group">
                <label className="text-muted">Mobile</label>
                <input onChange={handleChange('mobile')} type="text" value={mobile} className="form-control" />
            </div>            
            <div>
                <button type="submit" className="btn btn-primary">
                    Submit
                </button>
            </div>
        </form>
    );

    const showError = () => (
        <div className="alert alert-danger" style={{ display: error ? '' : 'none' }}>
            {error}
        </div>
    );

    const showSuccess = () => (
        <div className="alert alert-success" style={{ display: success ? '' : 'none' }}>
            Profile updated
        </div>
    );

    const showLoading = () => (
        <div className="alert alert-info" style={{ display: loading ? '' : 'none' }}>
            Loading...
        </div>
    );

    return (
        <React.Fragment>
            <div className="container">
                <div className="row">
                    <div className="col-md-4" >
                        <img
                            src={`/static/images/icon.png`}
                            className="img img-fluid img-thumbnail mb-3"
                            style={{ maxHeight: 'auto', maxWidth: '100%' }}
                            alt="user profile"
                        />
                    </div>
                    <div className="col-md-8 mb-5">
                        {showSuccess()}
                        {showError()}
                        {showLoading()}
                        {profileUpdateForm()}
                    </div>
                </div>
            </div>
        </React.Fragment>
    );
};

export default ProfileUpdate;
