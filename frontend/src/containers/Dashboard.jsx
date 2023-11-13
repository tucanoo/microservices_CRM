import React, {useContext} from 'react';
import {AuthContext} from "react-oauth2-code-pkce";
import {Link} from "react-router-dom";

const Dashboard = () => {
  const {idTokenData} = useContext(AuthContext);

  const isAdmin = idTokenData?.role === 'ADMIN';

  return (
    <div className="container" style={{marginTop: 80}}>
      <div>
        <h1>Welcome to simple crm</h1>
        <h2>Customer Management made Simple</h2>
      </div>

      <p className="mt-5"><Link to="/customer" className="btn btn-primary w-100">Manage Customers</Link></p>

      {isAdmin &&
        <p className="mt-5">
          <Link to="/user" className="btn btn-primary w-100">Manage Users</Link>
        </p>
      }
    </div>
  );
};

export default Dashboard;