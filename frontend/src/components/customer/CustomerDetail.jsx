const CustomerDetail = ({data}) => {

  return (
    <article>
      <div className="row">
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">First Name</label>
          <input className="form-control-plaintext" value={data.firstName} readOnly={true}/>

        </div>
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">Last Name</label>
          <input className="form-control-plaintext" value={data.lastName} readOnly={true}/>
        </div>
      </div>

      <div className="row">
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">Email Address</label>
          <input className="form-control-plaintext" value={data.emailAddress} readOnly={true}/>
        </div>
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">Phone Number</label>
          <input className="form-control-plaintext" value={data.phoneNumber} readOnly={true}/>
        </div>
      </div>

      <div className="row">
        <div className="form-group col mb-3">
          <label className="form-label fw-bold">Address</label>
          <input className="form-control-plaintext" value={data.address} readOnly={true}/>
        </div>
      </div>

      <div className="row">
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">City</label>
          <input className="form-control-plaintext" value={data.city} readOnly={true}/>
        </div>
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">Country</label>
          <input className="form-control-plaintext" value={data.country} readOnly={true}/>
        </div>
      </div>

    </article>
  );
};

export default CustomerDetail;