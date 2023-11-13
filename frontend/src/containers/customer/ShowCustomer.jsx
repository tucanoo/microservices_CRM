import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import axiosInstance from "../../network/API.js";
import {useParams} from "react-router";
import {CUSTOMER_API_URL} from "../../app/config.js";
import CustomerDetail from "../../components/customer/CustomerDetail.jsx";

const ShowCustomer = () => {

  const {id} = useParams();
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState(null);

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
        setError("Something went wrong. Please try again later.");
      } finally {
        setLoading(false);
      }
    }

    loadData();
  }, []);

  let content = <div className="text-center">Loading...</div>;

  if (error)
    content = <div className="text-center">{error}</div>;

  if (!loading && data)
    content = <CustomerDetail data={data}/>;

  return (
    <div className="container" style={{marginTop: 80}}>

      <h1 className="pb-2 border-bottom row">
        <span className="col-sm pb-4">Customer Details</span>
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

export default ShowCustomer;