import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import CustomerForm from "../../components/customer/CustomerForm.jsx";
import axiosInstance from "../../network/API.js";
import {useNavigate, useParams} from "react-router";
import {CUSTOMER_API_URL} from "../../app/config.js";

const EditCustomer = () => {

  const navigate = useNavigate();

  const [errors, setErrors] = useState([]);
  const [loadError, setLoadError] = useState([]);
  const {id} = useParams();
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState(null);

  const formSubmit = (data) => {
    axiosInstance.put(CUSTOMER_API_URL + "/customer", data)
      .then(response => {
        navigate("/customer", {state: {message: "Customer saved successfully"}})
      })
      .catch(error => {
        console.error(error)
        setErrors(["Something went wrong. Please try again later : " + error]);
      })
  }


  useEffect(() => {
    function loadData() {
      setLoading(true);
      try {
        axiosInstance.get(CUSTOMER_API_URL + "/customer/" + id)
          .then(response => {
            setData(response.data)
          })
      } catch (error) {
        console.error(error);
        setErrors(["Something went wrong. Please try again later."]);
      } finally {
        setLoading(false);
      }
    }

    loadData();
  }, []);

  let content = <div className="text-center">Loading...</div>;

  if (loadError)
    content = <div className="text-center">{loadError}</div>;

  if (!loading && data)
    content = <CustomerForm defaultValues={data} saveAction={formSubmit}/>;

  return (
    <div className="container" style={{marginTop: 80}}>

      <h1 className="pb-2 border-bottom row">
        <span className="col-sm pb-4">Edit Customer</span>
        <span className="col-12 col-sm-6 text-sm-end pb-4">
            <Link to="/customer" className="btn btn-primary d-block d-sm-inline-block">Back to list</Link>
        </span>
      </h1>

      <div className="mt-5 card card-body bg-light">
        {content}
      </div>
    </div>
  );
};

export default EditCustomer;