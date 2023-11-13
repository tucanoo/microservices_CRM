import React from 'react';
import * as yup from "yup";
import {useForm} from "react-hook-form";
import {yupResolver} from '@hookform/resolvers/yup';
import FormInput from "../forms/FormInput.jsx";
import FormSelect from "../forms/FormSelect.jsx";
import FormSwitch from "../forms/FormSwitch.jsx";

const EditUserForm = ({defaultValues, saveAction}) => {

  const formSchema = yup.object({
    fullName: yup.string().required(),
    role: yup.string().required(),
    username: yup.string().required(),
    enabled: yup.bool(),
  });

  const {register,control, formState: {errors}, handleSubmit} = useForm({
    mode: "onChange",
    defaultValues: defaultValues || {},
    resolver: yupResolver(formSchema)
  });

  return (
    <form onSubmit={handleSubmit(saveAction)} className="form">
      <div className="row">
        <div className="form-group col-12 col-sm-6  mb-3">
          <label className="form-label fw-bold">Full Name</label>
          <FormInput fieldName="fullName" register={register} errors={errors} params={{autoFocus: true}}/>

        </div>
        <div className="form-group col-12 col-sm-6  mb-3">
          <label className="form-label fw-bold">Role</label>
          <FormSelect
            fieldName="role"
            options={[{value: "ADMIN", label:"Administrator"}, {value: "USER", label:"Standard User"}, {value: "READONLY_USER", label:"Read-only User"}]}
            control={control} errors={errors}/>
        </div>
      </div>

      <div className="row">
        <div className="form-group col-12 col-sm-6 mb-3">
          <label className="form-label fw-bold">Username</label>
          <FormInput fieldName="username" register={register} errors={errors}/>
        </div>

        <div className="form-group col-12 col-sm-6 mb-3">
          <label className="form-label fw-bold">Enabled</label>
          <FormSwitch fieldName="enabled" register={register} errors={errors} />
        </div>
      </div>


      <div className="row mt-5">
        <div className="col">
          <button type="submit" className="btn btn-success w-100">
            {defaultValues?.id ? "Save Changes" : "Create User"}
          </button>
        </div>
      </div>
    </form>
  );
};

export default EditUserForm;