import Link from 'next/link';
import { useState, useEffect, useRef } from 'react';
import Router from 'next/router';
import dynamic from 'next/dynamic';
import { withRouter } from 'next/router';
import { getCookie, isAuth } from '../../actions/auth';
import { getCategories } from '../../actions/category';
import { getTags } from '../../actions/tag';
import { singleBlog, updateBlog } from '../../actions/blog';
const ReactQuill = dynamic(() => import('react-quill'), { ssr: false });
import '../../node_modules/react-quill/dist/quill.snow.css';
import { QuillModules, QuillFormats } from '../../helpers/quill';
import { API } from '../../config';

const BlogUpdate = ({ router }) => {


    const [bodyData, setBodyData] = useState('');

    const [categories, setCategories] = useState([]);
    const [tags, setTags] = useState([]);

    const [checked, setChecked] = useState([]); // categories
    const [checkedTag, setCheckedTag] = useState([]); // tags

    const [values, setValues] = useState({
        title: '',
        error: '',
        success: '',
        title: '',
        photo: '',
        categoriesData:'',
        body: '',
        tagsData: '',
        formData: ''
    });

    const { error, success, formData, title, photo ,body,categoriesData, tagsData} = values;
    const token = getCookie('token');

    useEffect(() => {
        // setValues({ ...values, formData: new FormData() });
        initBlog();
        initCategories();
        initTags();
    }, [router]);

    const initBlog = () => {
        if (router.query.slug) {
            singleBlog(router.query.slug).then(data => {
                console.log(data);
                if (!data) {
                    console.log(data.error);
                } else {
                    let cateCombine = [];
                    let tagCombine =[];
                    data.categories.forEach(c => {
                        cateCombine.push(c.id)
                    })
                    data.tags.forEach(c => {
                        tagCombine.push(c.id)
                    })
                    setValues({ ...values
                        , title: data.title 
                        , photo: data.photo
                        , categoriesData: cateCombine.join(",")
                        , tagsData: tagCombine.join(",")
                        , body: data.body
                        });
                    setBodyData(data.body);
                    setCategoriesArray(data.categories);
                    setTagsArray(data.tags);
                }
            });
        }
    };

    const setCategoriesArray = blogCategories => {
        let ca = [];
        console.log(blogCategories);
        blogCategories.map((c, i) => {
            ca.push(c.id);
        });
        setChecked(ca);
    };

    const setTagsArray = blogTags => {
        let ta = [];
        blogTags.map((t, i) => {
            ta.push(t.id);
        });
        setCheckedTag(ta);
    };

    const initCategories = () => {
        getCategories().then(data => {
            if (!data) {
                setValues({ ...values, error: data.error });
            } else {
                setCategories(data);
            }
        });
    };

    const initTags = () => {
        getTags().then(data => {
            if (!data) {
                setValues({ ...values, error: data.error });
            } else {
                setTags(data);
            }
        });
    };

    const handleToggle = c => () => {
        setValues({ ...values, error: '' });
        // return the first index or -1
        const clickedCategory = checked.indexOf(c);
        const all = [...checked];

        if (clickedCategory === -1) {
            all.push(c);
        } else {
            all.splice(clickedCategory, 1);
        }
        console.log(all);
        setChecked(all);
        let combine =[];
        all.forEach(a => {
            combine.push(a.id);
        })
        setValues({ ...values
            , categoriesData: combine.join(",")})
        // formData.set('categories', all);
    };

    const handleTagsToggle = t => () => {
        setValues({ ...values, error: '' });
        // return the first index or -1
        const clickedTag = checkedTag.indexOf(t);
        const all = [...checkedTag];

        if (clickedTag === -1) {
            all.push(t);
        } else {
            all.splice(clickedTag, 1);
        }
        console.log(all);
        setCheckedTag(all);
        // formData.set('tags', all);
        
        let combine =[];
        all.forEach(a => {
            combine.push(a.id);
        })
        setValues({ ...values
            , tagsData: combine.join(",")})
    };

    const findOutCategory = c => {
        const result = checked.indexOf(c);
        if (result !== -1) {
            return true;
        } else {
            return false;
        }
    };

    const findOutTag = t => {
        const result = checkedTag.indexOf(t);
        if (result !== -1) {
            return true;
        } else {
            return false;
        }
    };

    const showCategories = () => {
        return (
            categories &&
            categories.map((c, i) => (
                <li key={i} className="list-unstyled">
                    <input
                        onChange={handleToggle(c.id)}
                        checked={findOutCategory(c.id)}
                        type="checkbox"
                        className="mr-2"
                    />
                    <label className="form-check-label">{c.name}</label>
                </li>
            ))
        );
    };

    const showTags = () => {
        return (
            tags &&
            tags.map((t, i) => (
                <li key={i} className="list-unstyled">
                    <input
                        onChange={handleTagsToggle(t.id)}
                        checked={findOutTag(t.id)}
                        type="checkbox"
                        className="mr-2"
                    />
                    <label className="form-check-label">{t.name}</label>
                </li>
            ))
        );
    };

    const handleChange = name => e => {
        // console.log(e.target.value);
        if(name === 'photo'){
            const file = e.target.files[0] ;
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function () {
                const value = reader.result;
                debugger;
                // formData.set(name, value);
                setValues({ ...values, [name]: value, error: '' });
            };
            reader.onerror = function (error) {
                console.log('Error: ', error);
            };
        }else{
            // formData.set(name, e.target.value);
            setValues({ ...values, [name]: e.target.value, error: '' });
        }
    };

    const handleBody = e => {
        setBodyData(e);
        setValues({ ...values, body:  e });
            // try {
            //     formData.set('body', e);
            // } catch (error) {
            //     setValues({ ...values, formData: new FormData() });
            //     formData.set('body', e);
            // }
        
    };

    const editBlog = e => {
        e.preventDefault();
        const user = isAuth().id;
        updateBlog({title, body, photo, categories : categoriesData, tags : tagsData, user}, token, router.query.slug).then(data => {
            if (!data) {
                setValues({ ...values, error: "Something wrong" });
            } else {
                setValues({ ...values, title: '', success: `News titled "${data.title}" is successfully updated` });
                if (isAuth() && isAuth().roles.length === 2) {
                    // Router.replace(`/admin/crud/${router.query.slug}`);
                    Router.replace(`/admin`);
                } else if (isAuth() && isAuth().roles.length === 1) {
                    // Router.replace(`/user/crud/${router.query.slug}`);
                    Router.replace(`/user`);
                }
            }
        });
    };

    const showError = () => (
        <div className="alert alert-danger" style={{ display: error ? '' : 'none' }}>
            {error}
        </div>
    );

    const showSuccess = () => (
        <div className="alert alert-success" style={{ display: success ? '' : 'none' }}>
            {success}
        </div>
    );

    const updateBlogForm = () => {
        return (
            <form onSubmit={editBlog}>
                <div className="form-group">
                    <label className="text-muted">Title</label>
                    <input type="text" className="form-control" value={title} onChange={handleChange('title')} />
                </div>

                <div className="form-group">
                    <ReactQuill
                        modules={QuillModules}
                        formats={QuillFormats}
                        value={bodyData}
                        placeholder="Write something amazing..."
                        onChange={handleBody}
                    />
                </div>

                <div>
                    <button type="submit" className="btn btn-primary">
                        Update
                    </button>
                </div>
            </form>
        );
    };

    return (
        <div className="container-fluid pb-5">
            <div className="row">
                <div className="col-md-8">
                    {updateBlogForm()}

                    <div className="pt-3">
                        {showSuccess()}
                        {showError()}
                    </div>

                    {body && (
                        <img src={photo} alt={title} style={{ width: '100%' }} />
                    )}
                </div>

                <div className="col-md-4">
                    <div>
                        <div className="form-group pb-2">
                            <h5>Featured image</h5>
                            <hr />

                            <small className="text-muted">Max size: 1mb</small>
                            <br />
                            <label className="btn btn-outline-info">
                                Upload featured image
                                <input onChange={handleChange('photo')} type="file" accept="image/*" hidden />
                            </label>
                        </div>
                    </div>
                    <div>
                        <h5>Categories</h5>
                        <hr />

                        <ul style={{ maxHeight: '200px', overflowY: 'scroll' }}>{showCategories()}</ul>
                    </div>
                    <div>
                        <h5>Tags</h5>
                        <hr />
                        <ul style={{ maxHeight: '200px', overflowY: 'scroll' }}>{showTags()}</ul>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default withRouter(BlogUpdate);
