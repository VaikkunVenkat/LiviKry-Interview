import React, { useEffect, useState } from "react";
import MaterialTable from "material-table";
import { servicesColums, servicesData as data } from "../../fixtures/services";
import { IServicesData } from "../../fixtures/types";

import Container from "../Container";
import { API_URL } from "../../utils";

interface ITableProps {
  refresh: boolean
}

// TODO: Consider props for Table component
const Table: React.FC<ITableProps> = ({ refresh }) => {
  const [servicesData, setServicesData] = useState<IServicesData[]>(data);
  useEffect(() => {
    fetch(API_URL).then(res => res.json()).then((data: IServicesData[]) => setServicesData(data)).catch(error => alert(error.message));
  }, [refresh]) 
  
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
