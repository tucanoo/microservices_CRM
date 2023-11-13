import React from 'react';
import {Link} from "react-router-dom";
import {useLocation} from "react-router";
import UserGrid from "../../components/user/UserGrid.jsx";

const Users = () => {

  const location = useLocation();

  return (
    <div className="container" style={{marginTop: 80}}>
      <h1 className="pb-2 border-bottom row">
        <span className="col-12 col-sm-6 pb-4">User List</span>
        <span className="col-12 col-sm-6 text-sm-end pb-4">

            <Link to="/user/create"
                  className="btn btn-outline-primary d-block d-sm-inline-block me-2">Create User</Link>

          <Link to="/" className="btn btn-primary d-block d-sm-inline-block">Back</Link>
        </span>
      </h1>

      {location.state?.message && <div className="alert alert-success">
        <h3>{location.state?.message}</h3>
      </div>}

      <div className="mt-5">
        <UserGrid/>
      </div>
    </div>
  );
};

export default Users;