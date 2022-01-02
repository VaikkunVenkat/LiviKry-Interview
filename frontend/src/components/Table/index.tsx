import React from "react";
import MaterialTable from "material-table";
import { servicesColums, servicesData } from "../../fixtures/services";
import { IServicesData } from "../../fixtures/types";

import Container from "../Container";

// TODO: Consider props for Table component
const Table: React.FC<any> = () => {
  // TODO: define table's state.
  // Add a useEffect that fectches the the data from the database onLoad. 
  
  return (
    <Container>
      {(createService, updateService, deleteService) => (
        <MaterialTable
        title="Services"
        options={{
          filtering: true,
          sorting: true,
          exportButton: true,
        }}
        editable={{
          onRowAdd: (newData: IServicesData) => createService('CREATE'),
          onRowUpdate: (newData: IServicesData, oldData: any) => updateService('UPDATE'),
          onRowDelete: (oldData: IServicesData) => deleteService('DELETE')
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
      )}
    </Container>
    
  );
};

export default Table;
