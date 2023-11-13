import axiosInstance from "../../network/API.js";
import ReactDataGrid from "@inovua/reactdatagrid-community";
import SelectFilter from "@inovua/reactdatagrid-community/SelectFilter";
import '@inovua/reactdatagrid-community/index.css'
import {USER_API_URL} from "../../app/config.js";
import {Link} from "react-router-dom";

const UserGrid = () => {

  const getData = async ({skip, limit, sortInfo, filterValue}) => {
    const sortData = sortInfo ?? null;
    const result = await axiosInstance.get(USER_API_URL + "/user", {
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

  const statusFilterSources =
    [
      {id: "true", label: "Enabled"},
      {id: "false", label: "Disabled"}
    ]

  const columns = [
    {
      name: 'id',
      sortable: true,
      enableColumnFilterContextMenu: false,
      type: 'number',
    }, {
      name: 'username',
      header: 'Username',
      sortable: true,
      render: ({data}) => <Link to={"/user/edit/" + data.id}>{data.username}</Link>
    }, {
      name: 'fullName',
      header: 'Full Name',
      flex: 1,
      sortable: true,
    }, {
      name: 'enabled',
      header: 'Enabled',
      sortable: true,
      render: ({data}) => data.enabled ? <i className="bi-check text-success fs-2"/> :
        <i className="bi-x text-danger fs-2"/>,
      enableColumnFilterContextMenu: false,
      filterEditor: SelectFilter,
      filterEditorProps: {
        dataSource: statusFilterSources
      },
    }, {
      name: 'dateCreated',
      header: 'Date created',
      sortable: true,
      render: ({data}) => new Date(data.dateCreated).toLocaleString()
    }
  ]

  const filterValue = [
    {name: 'id', operator: 'eq', type: 'number', value: ''},
    {name: 'username', operator: 'contains', type: 'string', value: ''},
    {name: 'fullName', operator: 'contains', type: 'string', value: ''},
    {name: 'enabled', operator: 'eq', type: 'boolean', value: ''}
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

export default UserGrid;