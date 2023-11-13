import React, {useContext} from 'react';
import {AuthContext} from "react-oauth2-code-pkce";

const Navbar = () => {
  const {token, logOut } = useContext(AuthContext);

  return (
    <nav className="navbar navbar-dark bg-dark fixed-top justify-content-between px-4">
      <a className="navbar-brand" href="/">Simple CRM</a>
      {token && <a className="mr-auto btn btn-sm btn-outline-light" href="/logout" onClick={() => logOut()} >Logout</a>}
    </nav>
  );
};

export default Navbar;