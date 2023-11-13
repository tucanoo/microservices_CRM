import {Outlet} from "react-router-dom";
import {Navigate} from "react-router";
import {useContext} from "react";
import {AuthContext} from "react-oauth2-code-pkce";

const ProtectedRoute = () => {

  const { token } = useContext(AuthContext)

  return token ? <Outlet/> : <Navigate to="/" replace/>

};

export default ProtectedRoute;