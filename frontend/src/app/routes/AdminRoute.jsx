import {Outlet} from "react-router-dom";
import {Navigate} from "react-router";
import {useContext} from "react";
import {AuthContext} from "react-oauth2-code-pkce";

const AdminRoute = () => {
  const {token, idTokenData} = useContext(AuthContext)

  if (token && idTokenData?.role === 'ADMIN')
    return <Outlet/>
  else
    return <Navigate to="/" replace/>

};

export default AdminRoute;