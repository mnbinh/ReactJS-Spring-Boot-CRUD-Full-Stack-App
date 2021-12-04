import Layout from '../../components/Layout';
import Private from '../../components/auth/Private';
import Link from 'next/link';

const UserIndex = () => {
    return (
        <Layout>
            <Private>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-12 pt-5 pb-5">
                            <h2>User Dashboard</h2>
                        </div>
                        <div className="col-md-4">
                            <ul class="list-group">
                                <li className="list-group-item">
                                    <a href="/user/crud/blog">Write News</a>
                                </li>

                                <li className="list-group-item">
                                    <Link href="/user/crud/blogs">
                                        <a>List News</a>
                                    </Link>
                                </li>

                                <li className="list-group-item">
                                    <a href="/user">Update profile</a>
                                </li>
                            </ul>
                        </div>
                        <div className="col-md-8"></div>
                    </div>
                </div>
            </Private>
        </Layout>
    );
};

export default UserIndex;
