import React from 'react';
import PropTypes from "prop-types";

const FormInput = ({register, fieldName, errors, params}) => {
  const inputClasses = ['form-control'];
  if (errors[fieldName]?.message) inputClasses.push('is-invalid');

  return <>
    <input className={inputClasses.join(' ')}
           {...register(fieldName)}
           {...params}
           aria-invalid={errors[fieldName]?.message ? "true" : "false"}
    />
    {errors[fieldName]?.message && <p role="alert">{errors[fieldName].message}</p>}
  </>
}

FormInput.propTypes = {
  fieldName: PropTypes.string.isRequired,  // name of the field
  errors: PropTypes.object,          // the errors object from react-hook-form
  register: PropTypes.func.isRequired,      // the register function from react-hook-form
  params: PropTypes.object,         // any additional parameters to pass to the input field
};

export default FormInput;