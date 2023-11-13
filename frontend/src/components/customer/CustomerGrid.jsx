import React, {useContext} from 'react';
import {AuthContext} from "react-oauth2-code-pkce";
import axiosInstance from "../../network/API.js";
import ReactDataGrid from "@inovua/reactdatagrid-community";
import '@inovua/reactdatagrid-community/index.css'
import {CUSTOMER_API_URL} from "../../app/config.js";
import {Link} from "react-router-dom";

const CustomerGrid = () => {
  const {idTokenData} = useContext(AuthContext);
  const canUserEdit = 'READONLY_USER' !== idTokenData?.role;

  const getData = async ({skip, limit, sortInfo, filterValue}) => {
    const sortData = sortInfo ?? null;
    const result = await axiosInstance.get(CUSTOMER_API_URL + "/customer", {
      params: {
        start: skip,
        length: limit,
        filter: encodeURIComponent(JSON.stringify(filterValue)),
        sort: encodeURIComponent(JSON.stringify(sortData))
      }
    })
    console.log("RESPONSE ", result)
    const data = result.data.data;
    const total = result.data.recordsTotal;
    return {data, count: parseInt(total)}

  }

  const linkPrefix = canUserEdit ? '/customer/edit/' : '/customer/';

  const columns = [
    {
      name: 'id',
      sortable: true,
      type: 'number',
      render: ({data}) => <Link to={linkPrefix + data.id}>{data.id}</Link>
    }, {
      name: 'firstName',
      header: 'First Name',
      sortable: true,
      render: ({data}) => <Link to={linkPrefix + data.id}>{data.firstName}</Link>
    }, {
      name: 'lastName',
      header: 'Last Name',
      sortable: true,
      render: ({data}) => <Link to={linkPrefix + data.id}>{data.lastName}</Link>
    }, {
      name: 'emailAddress',
      header: 'Email',
      flex: 1,
      sortable: true,
    }, {
      name: 'city',
      header: 'City',
      sortable: true,
    }, {
      name: 'country',
      header: 'Country',
      sortable: true,
    }, {
      name: 'phoneNumber',
      header: 'Phone',
      sortable: true,
    }
  ]

  const filterValue = [
    {name: 'id', operator: 'eq', type: 'number', value: ''},
    {name: 'firstName', operator: 'contains', type: 'string', value: ''},
    {name: 'lastName', operator: 'contains', type: 'string', value: ''},
    {name: 'emailAddress', operator: 'contains', type: 'string', value: ''},
    {name: 'city', operator: 'contains', type: 'string', value: ''},
    {name: 'country', operator: 'contains', type: 'string', value: ''},
    {name: 'phoneNumber', operator: 'contains', type: 'string', value: ''}
  ];

  return (
    <div>
      <ReactDataGrid
        idProperty="id"
        style={{minHeight: 550}}
        columns={columns}
        defaultSortInfo={{name: 'id', dir: 1}}
        dataSource={getData}
        defaultFilterValue={filterValue}
        pagination
        defaultLimit={10}
      />
    </div>
  );
};

export default CustomerGrid;