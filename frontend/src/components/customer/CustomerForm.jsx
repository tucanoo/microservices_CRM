import React from 'react';
import * as yup from "yup";
import {useForm} from "react-hook-form";
import {yupResolver} from '@hookform/resolvers/yup';
import FormInput from "../forms/FormInput.jsx";

const CustomerForm = ({defaultValues, saveAction}) => {

  const formSchema = yup.object({
    firstName: yup.string().required(),
    lastName: yup.string().required(),
    emailAddress: yup.string().required().email(),
    phoneNumber: yup.string(),
    address: yup.string(),
    city: yup.string(),
    country: yup.string(),
  });

  const {register, formState: {errors}, handleSubmit} = useForm({
    mode: "onChange",
    defaultValues: defaultValues || {},
    resolver: yupResolver(formSchema)
  });

  return (
    <form onSubmit={handleSubmit(saveAction)} className="form">
      <div className="row">
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">First Name</label>
          <FormInput fieldName="firstName" register={register} errors={errors} params={{autoFocus: true}}/>

        </div>
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">Last Name</label>
          <FormInput fieldName="lastName" register={register} errors={errors}/>
        </div>
      </div>

      <div className="row">
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">Email Address</label>
          <FormInput fieldName="emailAddress" register={register} errors={errors}/>
        </div>
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">Phone Number</label>
          <FormInput fieldName="phoneNumber" register={register} errors={errors}/>
        </div>
      </div>

      <div className="row">
        <div className="form-group col mb-3">
          <label className="form-label fw-bold">Address</label>
          <FormInput fieldName="phoneNumber" register={register} errors={errors}/>
        </div>
      </div>

      <div className="row">
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">City</label>
          <FormInput fieldName="city" register={register} errors={errors}/>
        </div>
        <div className="form-group col-6 mb-3">
          <label className="form-label fw-bold">Country</label>
          <FormInput fieldName="country" register={register} errors={errors}/>
        </div>
      </div>

      <div className="row mt-5">
        <div className="col">
          <button type="submit" className="btn btn-success w-100">
            {defaultValues?.id ? "Save Changes" : "Create Customer"}
          </button>
        </div>
      </div>
    </form>
  );
};

export default CustomerForm;