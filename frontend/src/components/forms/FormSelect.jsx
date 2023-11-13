import React from 'react';
import PropTypes from "prop-types";
import Select from "react-select";
import {Controller} from "react-hook-form";

const FormSelect = ({control, fieldName, errors, params, options}) => {
  const inputClasses = ['form-control-select'];
  if (errors[fieldName]?.message) inputClasses.push('is-invalid');

  return <>
    <Controller
      name={fieldName}
      control={control}
      render={({field}) => <Select
        className={inputClasses.join(' ')}
        {...field}
        {...params}
        options={options}
        aria-invalid={errors[fieldName]?.message ? "true" : "false"}
        value={options.filter(c => field?.value === c.value)}
        onChange={(val) => {
          field.onChange(val.value)
        }}
      />}
    />

    {errors[fieldName]?.message && <p role="alert">{errors[fieldName].message}</p>}
  </>
}

FormSelect.propTypes = {
  fieldName: PropTypes.string.isRequired,  // name of the field
  errors: PropTypes.object,          // the errors object from react-hook-form
  control: PropTypes.object.isRequired,      // the control function from react-hook-form
  params: PropTypes.object,         // any additional parameters to pass to the input field
  options: PropTypes.array.isRequired,   // the options to display in the select
};

export default FormSelect;