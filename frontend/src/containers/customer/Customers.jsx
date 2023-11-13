import React, {useContext} from 'react';
import {AuthContext} from "react-oauth2-code-pkce";
import {Link} from "react-router-dom";
import {useLocation} from "react-router";
import CustomerGrid from "../../components/customer/CustomerGrid.jsx";

const Customers = () => {

  const location = useLocation();
  const {idTokenData} = useContext(AuthContext);

  const canCreateCustomers = ['ADMIN', 'USER'].includes(idTokenData?.role);

  return (
    <div className="container" style={{marginTop: 80}}>
      <h1 className="pb-2 border-bottom row">
        <span className="col-12 col-sm-6 pb-4">Customer List</span>
        <span className="col-12 col-sm-6 text-sm-end pb-4">
          {canCreateCustomers &&
            <Link to="/customer/create"
                  className="btn btn-outline-primary d-block d-sm-inline-block me-2">Create Customer</Link>
          }
          <Link to="/" className="btn btn-primary d-block d-sm-inline-block">Back</Link>
        </span>
      </h1>

      {location.state?.message && <div className="alert alert-success">
        <h3>{location.state?.message}</h3>
      </div>}

      <div className="mt-5">
        <CustomerGrid />
      </div>
    </div>
  );
};

export default Customers;