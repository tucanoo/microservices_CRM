import Login from "./containers/auth/Login.jsx";
import Navbar from "./components/layout/Navbar.jsx";
import Footer from "./components/layout/Footer.jsx";
import {Navigate, Routes, useLocation, useNavigate} from "react-router";
import {Route} from "react-router-dom";
import ProtectedRoute from "./app/routes/ProtectedRoute.jsx";
import Dashboard from "./containers/Dashboard.jsx";
import {AUTH_TOKEN_NAME, LOGIN_URL, REDIRECT_URL, TOKEN_URL} from "./app/config.js";
import {AuthProvider} from "react-oauth2-code-pkce";
import OauthCallback from "./components/auth/OauthCallback.jsx";
import Customers from "./containers/customer/Customers.jsx";
import {setDefaultHeader} from "./network/API.js";
import CreateCustomer from "./containers/customer/CreateCustomer.jsx";
import ShowCustomer from "./containers/customer/ShowCustomer.jsx";
import EditCustomer from "./containers/customer/EditCustomer.jsx";
import Users from "./containers/user/Users.jsx";
import AdminRoute from "./app/routes/AdminRoute.jsx";
import "bootstrap-icons/font/bootstrap-icons.css";
import CreateUser from "./containers/user/CreateUser.jsx";
import EditUser from "./containers/user/EditUser.jsx";

function App() {

  history.navigate = useNavigate();
  history.location = useLocation();

  const authConfig = {
    clientId: 'frontend',
    authorizationEndpoint: LOGIN_URL,
    tokenEndpoint: TOKEN_URL,
    redirectUri: REDIRECT_URL,
    scope: 'openid',
    autoLogin: false,
    decodeToken: true,
    storageKeyPrefix: AUTH_TOKEN_NAME + '_',
    onRefreshTokenExpire: (event) => window.confirm('Session expired. Refresh page to continue using the site?') && event.login(),
    postLogin: () => {
      const token = localStorage.getItem(AUTH_TOKEN_NAME + "_token").substring(1, localStorage.getItem(AUTH_TOKEN_NAME + "_token").length - 1);
      // set default header for axios API calls
      setDefaultHeader('Authorization', 'Bearer ' + token)
    }
  }

  return (
    <>
      <AuthProvider authConfig={authConfig}>
        <Navbar/>
        <Routes>
          <Route path="/" element={<Login/>}/>
          <Route path="/auth/callback" element={<OauthCallback/>}/>

          <Route element={<ProtectedRoute/>}>
            <Route path="/home" element={<Dashboard/>}/>
            <Route path="/customer">
              <Route index element={<Customers/>}/>
              <Route path="create" element={<CreateCustomer/>}/>
              <Route path="edit/:id" element={<EditCustomer/>}/>
              <Route path=":id" element={<ShowCustomer/>}/>
            </Route>
          </Route>

          <Route element={<AdminRoute/>}>
            <Route path="/user">
              <Route index element={<Users/>}/>
              <Route path="create" element={<CreateUser/>}/>
              <Route path="edit/:id" element={<EditUser/>}/>
            </Route>
          </Route>

          <Route path="*" element={<Navigate to="/"/>}/>
        </Routes>
        <Footer/>
      </AuthProvider>
    </>
  )
}

export default App
