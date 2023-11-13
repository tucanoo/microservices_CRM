import React from 'react';
import PropTypes from "prop-types";
import {Form} from "react-bootstrap";

const FormSwitch = ({register, fieldName, errors, params}) => {
  const isInvalid = !!errors[fieldName]?.message;


  return <>
    <Form.Switch
                 id={fieldName}
                 label=""
                 {...register(fieldName)}
                 {...params}
                 isInvalid={isInvalid}
    />
    {isInvalid && <p role="alert">{errors[fieldName].message}</p>}
  </>
}

FormSwitch.propTypes = {
  fieldName: PropTypes.string.isRequired,  // name of the field
  errors: PropTypes.object,          // the errors object from react-hook-form
  register: PropTypes.func.isRequired,      // the register function from react-hook-form
  params: PropTypes.object,         // any additional parameters to pass to the input field
};

export default FormSwitch;