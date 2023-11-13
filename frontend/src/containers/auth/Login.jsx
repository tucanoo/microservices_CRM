import React, {useContext} from 'react';
import {AuthContext} from "react-oauth2-code-pkce";
import {Navigate} from "react-router";

const Login = () => {
  const {  token, login, } = useContext(AuthContext);

  if (token)
    return <Navigate to={ '/home' } replace={ true } />

  return (
    <div className="container" style={{marginTop:80}}>
      <div className="mb-5">
        <h1>Welcome to Simple CRM</h1>
        <h2>Customer Management made Simple</h2>
      </div>

      <div className="row">
        <div className="col-12 col-md-4 offset-md-4">
          <div className="card">
            <article className="card-body">
              <h4 className="card-title text-center mb-4 mt-1">Sign in</h4>
              <hr/>
              <a onClick={() => login()} className="btn btn-primary w-100">Login</a>
            </article>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;