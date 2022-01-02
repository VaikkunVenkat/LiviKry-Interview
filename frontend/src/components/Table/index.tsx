import React from "react";
import MaterialTable from "material-table";
import { servicesColums } from "../../fixtures/services";
import { ITableProps } from "./types";


const Table: React.FC<ITableProps> = ({ createService, updateService, deleteService, servicesData }) => {
  
  return (
    <MaterialTable
      title="Services"
      options={{
        filtering: true,
        sorting: true,
        exportButton: true,
      }}
      editable={{
        onRowAdd: createService,
        onRowUpdate: updateService,
        onRowDelete: deleteService
      }}
      localization={{
        header : {
            actions: '',
        },
        body: {
          addTooltip: 'Add an API service',
          deleteTooltip: 'Delete an API service',
          editTooltip: 'Edit an API service'
        }
      }}
      columns={servicesColums}
      data={servicesData}
    />
  );
};

export default Table;
