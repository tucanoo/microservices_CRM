import React, {useState} from 'react';
import {Link} from "react-router-dom";
import CustomerForm from "../../components/customer/CustomerForm.jsx";
import axiosInstance from "../../network/API.js";
import {useNavigate} from "react-router";
import {CUSTOMER_API_URL} from "../../app/config.js";

const CreateCustomer = () => {

  const navigate = useNavigate();

  const [errors, setErrors] = useState([]);

  const formSubmit = (data) => {
    axiosInstance.post(CUSTOMER_API_URL + "/customer", data)
      .then(response => {
        navigate( "/customer", {state: {message: "Customer created successfully"}})
      })
      .catch(error => {
        console.error(error)
        setErrors(["Something went wrong. Please try again later : " + error]);
      })
  }

  return (
    <div className="container" style={{marginTop:80}}>

      <h1 className="pb-2 border-bottom row">
        <span className="col-sm pb-4">New Customer Details</span>
        <span className="col-12 col-sm-6 text-sm-end pb-4">
            <Link to="/customer" className="btn btn-primary d-block d-sm-inline-block">Back to list</Link>
        </span>
      </h1>


      <div className="mt-5 card card-body bg-light">
        <CustomerForm saveAction={formSubmit}/>
      </div>
    </div>
  );
};

export default CreateCustomer;