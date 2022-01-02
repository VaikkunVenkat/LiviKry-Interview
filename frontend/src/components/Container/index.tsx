import React, { useState, useEffect } from "react";
import { IServicesData } from "../../fixtures/types";
import { servicesData as data } from "../../fixtures/services";
import { API_URL, APP_REFRESH_URL, filterArrObjects } from "../../utils";
import { IContainerProps } from "./types";

const Container: React.FC<IContainerProps> = ({ children, refresh }) => {

  useEffect(() => {
    fetch(APP_REFRESH_URL).then(res => res.json()).then((data: IServicesData[]) => setServicesData(data)).catch(error => console.error(error.message));
  }, [refresh]) 

  const [servicesData, setServicesData] = useState<IServicesData[]>(data);

  const createService = (newData: IServicesData): Promise<any> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        fetch(API_URL, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(newData)
        })
          .then(response => response.json())
          .then(data => {
            setServicesData([...servicesData, data]);
          });
        resolve(servicesData);
      }, 1000);
    })
  }

  const updateService = (newData: IServicesData, oldData?: IServicesData): Promise<any> => {
    return new Promise((resolve, reject) => {
      const filteredData: IServicesData[] = filterArrObjects(servicesData, oldData, 'name');
      setTimeout(() => {
        fetch(`${API_URL}/${newData.id}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(newData)
        })
          .then(response => response.json())
          .then(data => {
            setServicesData([...filteredData, data]);
          });
        resolve(servicesData);
      }, 1000);
    })
  }

  const deleteService = (oldData: IServicesData): Promise<any> => {
    return new Promise((resolve, reject) => {
      const filteredData: IServicesData[] = filterArrObjects(servicesData, oldData, 'name');
      setTimeout(() => {
        fetch(`${API_URL}/${oldData.id}`, {
          method: 'DELETE',
        })
          .then(() => setServicesData(filteredData))
        resolve(servicesData);
      }, 1000);
    })
  }
  return (
    <div id="container">
      {children({ createService, updateService, deleteService, servicesData })}
    </div>
  );
};

export default Container;
